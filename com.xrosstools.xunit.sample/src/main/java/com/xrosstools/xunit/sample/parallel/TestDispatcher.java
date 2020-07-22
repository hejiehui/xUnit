package com.xrosstools.xunit.sample.parallel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.TaskType;

public class TestDispatcher implements Dispatcher {
    private CompletionMode mode;
    private long timeout;
    private TimeUnit timeUnit;
    
    @Override
    public void setCompletionMode(CompletionMode mode) {
        this.mode = mode;
    }

    @Override
    public CompletionMode getCompletionMode() {
        return mode;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public Map<String, Context> dispatch(Context ctx, Map<String, TaskType> taskInfoMap) {
        ParallelContext parallelCtx = (ParallelContext)ctx;
        Map<String, Context> contextMap = new HashMap<>();
        for(Map.Entry<String, TaskType> taskInfo: taskInfoMap.entrySet()) {
            String taskId = taskInfo.getKey();
            contextMap.put(taskId, parallelCtx.taskCtxMap.get(taskId));
        }
        return contextMap;
    }

    @Override
    public Context onInvokeError(Map<String, Context> dispatchedContexts, Exception exception) {
        ParallelContext errorCtx = new ParallelContext();
        errorCtx.fail = true;
        errorCtx.exception = exception;
        return errorCtx;
    }

    @Override
    public Context onTaskError(Context inputCtx, Exception exception) {
        TaskContext ctx = (TaskContext)inputCtx;
        
        TaskContext newCtx = new TaskContext();
        newCtx.taskId = ctx.taskId;
        newCtx.success.set(false);
        return ctx;
    }

    @Override
    public Context merge(Context inputCtx, List<Context> results) {
        ParallelContext ctx = new ParallelContext();
        for(Context result: results) {
            TaskContext taskCtx = (TaskContext)result;
            if(taskCtx.success.get())
                ctx.sucessTasks.add(taskCtx.taskId);
            else
                ctx.failedTasks.add(taskCtx.taskId);
            
            ctx.results.add(taskCtx.result.get());
        }
        
        ParallelContext paraCtx = (ParallelContext)inputCtx;
        paraCtx.sucessTasks.addAll(ctx.sucessTasks);
        paraCtx.failedTasks.addAll(ctx.failedTasks);
        paraCtx.results.addAll(ctx.results);
        
        return ctx;
    }
}
