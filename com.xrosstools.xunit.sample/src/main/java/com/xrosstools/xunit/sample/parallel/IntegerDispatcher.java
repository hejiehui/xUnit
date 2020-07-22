package com.xrosstools.xunit.sample.parallel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.TaskType;
import com.xrosstools.xunit.sample.unit.IntegerContext;

public class IntegerDispatcher implements Dispatcher {
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
        IntegerContext ic = (IntegerContext)ctx;
        Map<String, Context> dispatchCtx = new HashMap<>();
        boolean first = true;
        for(String id: taskInfoMap.keySet()) {
            if(first) {
                dispatchCtx.put(id, new IntegerContext(ic.getValue()));
                first = false;
            }
            else
                dispatchCtx.put(id, new IntegerContext(0));
        }
        return dispatchCtx;
    }

    @Override
    public Context merge(Context inputCtx, List<Context> results) {
        int v = 0;
        for(Context r: results) {
            IntegerContext ic = (IntegerContext)r;
            v += ic.getValue();
        }
        IntegerContext inputIc = (IntegerContext)inputCtx;
        inputIc.setValue(v);
        return new IntegerContext(v);
    }

    @Override
    public Context onInvokeError(Map<String, Context> dispatchedContexts, Exception exception) {
        return new IntegerContext(0);
    }

    @Override
    public Context onTaskError(Context taskCtx, Exception exception) {
        return new IntegerContext(0);
    }
}
