package com.xrosstools.xunit.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.ParallelBranch;
import com.xrosstools.xunit.TaskType;
import com.xrosstools.xunit.Unit;

public class ParallelBranchImpl extends BaseCompositeImpl implements ParallelBranch {
    private static final int MAX_POOL_SIZE = 500;
    private static final int KEEP_ALIVE_TIME = 5;
    
    private static final int NO_TIMEOUT = 0;
    
    private static final String WORKER_THREAD_NAME = "Xunit-ParallelBranch-Worker-";
    private static final String SHUTDOWN_THREAD_NAME = "DAS-Server-Configure-Factory-ShutdownHook";

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            AtomicInteger atomic = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, WORKER_THREAD_NAME + this.atomic.getAndIncrement());
            }
        });
        executor.allowCoreThreadTimeOut(true);
        executorService = executor;
        
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName(SHUTDOWN_THREAD_NAME);
                executorService.shutdown();
            }
        }));
    }

    private static ExecutorService executorService;
    
    private Dispatcher dispatcher;
    
    private Map<String, Unit> mandatoryUnitMap = new ConcurrentHashMap<>();
    private Map<String, Unit> standaloneUnitMap = new ConcurrentHashMap<>();
    private Map<String, Unit> normalUnitMap = new ConcurrentHashMap<>();
    private Map<String, TaskType> unitTypeMap = new ConcurrentHashMap<>();
    
    @Override
    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void add(String taskId, TaskType type, Unit unit) {
        if(unitTypeMap.containsKey(taskId))
            throw new IllegalArgumentException("Duplicate task detected with id:" + taskId);
        
        unitTypeMap.put(taskId, type);
        
        if(type == TaskType.standalone)
            standaloneUnitMap.put(taskId, unit);
        else if(type == TaskType.normal)
            normalUnitMap.put(taskId, unit);
        else if(type == TaskType.mandatory)
            mandatoryUnitMap.put(taskId, unit);
        else
            throw new IllegalArgumentException("TaskType " + type + " is unsupported");
    }
    
    public void validate() {
        CompletionMode mode = dispatcher.getCompletionMode();
        String errorMsg = null;
        
        switch (mode) {
        case all:
        case any:
            if (normalUnitMap.isEmpty() && mandatoryUnitMap.isEmpty())
                errorMsg = "No normal or mandatory";
            break;
        case critical:
            if (mandatoryUnitMap.isEmpty())
                errorMsg = "No mandatory";
            break;
        case none:
            if (standaloneUnitMap.isEmpty() && normalUnitMap.isEmpty() && mandatoryUnitMap.isEmpty())
                errorMsg = "No";
            break;
        default:
            throw new IllegalArgumentException("CompletionMode " + dispatcher.getCompletionMode() + " is not supported");
        }
        
        if(errorMsg != null)
            throw new IllegalArgumentException(errorMsg + " task defined for parallel branch under mode " + mode);
    }
    
    public void process(Context ctx){
        execute(BehaviorType.processor, ctx);
    }
    
    public Context convert(Context inputCtx) {
        return execute(BehaviorType.converter, inputCtx);
    }
    
    protected Context execute(BehaviorType type, Context inputCtx) {
        Map<String, Context> dispatchedContexts = dispatcher.dispatch(inputCtx, new HashMap<>(unitTypeMap));

        switch (dispatcher.getCompletionMode()) {
        case all:
            return executeAll(type, inputCtx, dispatchedContexts);
        case any:
            return executeAny(type, inputCtx, dispatchedContexts);
        case critical:
            return executeCritical(type, inputCtx, dispatchedContexts);
        case none:
            return executeNone(type, inputCtx, dispatchedContexts);
        default:
            throw new IllegalArgumentException("CompletionMode " + dispatcher.getCompletionMode() + " is not supported");
        }
    }
    
    protected Context executeAll(BehaviorType type, Context inputCtx, Map<String, Context> dispatchedContexts) {
        submitStandalone(type, inputCtx, dispatchedContexts);
        
        List<XunitTask> tasks = dispatch(type, dispatchedContexts, combine(mandatoryUnitMap, normalUnitMap));
        
        List<Future<Context>> results;
        try {
            results = invokeAll(tasks);
        } catch (Exception e) {
            return dispatcher.onInvokeError(dispatchedContexts, e);
        }

        return dispatcher.merge(inputCtx, extractResults(tasks, results));
    }
    
    protected Context executeAny(BehaviorType type, Context inputCtx, Map<String, Context> dispatchedContexts) {
        submitStandalone(type, inputCtx, dispatchedContexts);
        
        List<XunitTask> tasks = dispatch(type, dispatchedContexts, combine(normalUnitMap, mandatoryUnitMap));
        
        List<Context> results = new ArrayList<>();
        try {
            if(isNoTimeout()) {
                results.add(executorService.invokeAny(tasks));
            } else {
                results.add(executorService.invokeAny(tasks, getTimeout(), getTimeUnit()));
            }
        } catch (Exception e) {
            return dispatcher.onInvokeError(dispatchedContexts, e);
        }
        
        return dispatcher.merge(inputCtx, results);
    }

    protected Context executeCritical(BehaviorType type, Context inputCtx, Map<String, Context> dispatchedContexts) {
        submitStandalone(type, inputCtx, dispatchedContexts);
        
        //We need first invoke none critical tasks to avoid block critical tasks
        List<XunitTask> nonCriticalTasks = dispatch(type, dispatchedContexts, normalUnitMap);
        List<Future<Context>> nonCriticalResults = new ArrayList<>();
        for(XunitTask task: nonCriticalTasks) {
            nonCriticalResults.add(executorService.submit(task));
        }
        
        List<XunitTask> criticalTasks = dispatch(type, dispatchedContexts, mandatoryUnitMap);
        List<Future<Context>> criticalResults = null;
        try {
            criticalResults = invokeAll(criticalTasks);
        } catch (Exception e) {
            return dispatcher.onInvokeError(dispatchedContexts, e);
        }
        
        List<Future<Context>> allResults = new ArrayList<>(criticalResults);
        allResults.addAll(nonCriticalResults);
        
        List<XunitTask> allTasks = new ArrayList<>(criticalTasks);
        allTasks.addAll(nonCriticalTasks);

        return dispatcher.merge(inputCtx, extractResults(allTasks, allResults));
    }

    /**
     * In this case, all kinds of tasks are combined and submitted without timeout
     */
    protected Context executeNone(final BehaviorType type, final Context inputCtx, Map<String, Context> dispatchedContexts) {
        submitStandalone(type, inputCtx, dispatchedContexts, combine(mandatoryUnitMap, standaloneUnitMap, normalUnitMap));
        
        return dispatcher.merge(inputCtx, new ArrayList<Context>());
    }
    
    private List<XunitTask> dispatch(BehaviorType type, Map<String, Context> dispatchedContexts, Map<String, Unit> unitMap) {
        List<XunitTask> tasks = new ArrayList<>(unitMap.size());
        
        for(Map.Entry<String, Unit> entry: unitMap.entrySet()) {
            String taskId = entry.getKey();

            Context ctx = dispatchedContexts.get(taskId);
            Unit unit = unitMap.get(taskId);
            
            if(ctx == null || unit == null)
                continue;
            
            tasks.add(new XunitTask(type, ctx, unit));
        }
        return tasks;
    }

    private void submitStandalone(BehaviorType type, Context inputCtx, Map<String, Context> dispatchedContexts) {
        submitStandalone(type, inputCtx, dispatchedContexts, standaloneUnitMap);
    }
    
    private void submitStandalone(BehaviorType type, Context inputCtx, final Map<String, Context> dispatchedContexts, Map<String, Unit> unitMap) {
        if(unitMap.isEmpty())
            return;
        
        final List<XunitTask> tasks = dispatch(type, dispatchedContexts, unitMap);
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    invokeAll(tasks);
                } catch (InterruptedException e) {
                    dispatcher.onInvokeError(dispatchedContexts, e);
                }
            }
        });
    }
    
    private List<Future<Context>> invokeAll(List<XunitTask> tasks) throws InterruptedException {
        if(isNoTimeout()) {
            return executorService.invokeAll(tasks);
        } else {
            return executorService.invokeAll(tasks, getTimeout(), getTimeUnit());
        }
    }

    private List<Context> extractResults(List<XunitTask> tasks, List<Future<Context>> futureResults) {
        List<Context> results = new ArrayList<>();
        for(int i = 0; i < tasks.size(); i++) {
            Future<Context> taskFuture = futureResults.get(i);
            if(taskFuture.isDone()) {
                try {
                    results.add(taskFuture.get());
                } catch (Exception e) {
                    results.add(dispatcher.onTaskError(tasks.get(i).ctx, e));
                }
            }
        }
        return results;
    }

    private long getTimeout() {
        return dispatcher.getTimeout();
    }
    
    private TimeUnit getTimeUnit() {
        return dispatcher.getTimeUnit();
    }

    private boolean isNoTimeout() {
        return isNoTimeout(getTimeout());
    }
    
    private boolean isNoTimeout(long timeout) {
        return timeout <= NO_TIMEOUT;
    }
    
    private Map<String, Unit> combine(Map<String, Unit>... unitMaps) {
        Map<String, Unit> combinedUnitMap = new HashMap<>();
        for(Map<String, Unit> map: unitMaps)
            combinedUnitMap.putAll(map);
        return combinedUnitMap;
    }

    private class XunitTask implements Callable<Context> {
        private BehaviorType type;
        private Context ctx;
        private Unit unit;
        public XunitTask(BehaviorType type, Context ctx, Unit unit) {
            this.type = type;
            this.ctx = ctx;
            this.unit = unit;
        }

        @Override
        public Context call() throws Exception {
            if(type == BehaviorType.processor) {
                process(unit, ctx);
                return ctx;
            } else {
                return convert(unit, ctx);
            }
        }
    }
}