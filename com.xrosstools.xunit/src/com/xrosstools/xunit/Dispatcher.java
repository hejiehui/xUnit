package com.xrosstools.xunit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface Dispatcher extends Unit{
    void setCompletionMode(CompletionMode mode);
    CompletionMode getCompletionMode();
    
    void setTimeout(long timeout);
    long getTimeout();
    
    void setTimeUnit(TimeUnit timeUnit);
    TimeUnit getTimeUnit();
    
    Map<String, Context> dispatch(Context inputCtx, Map<String, TaskType> taskInfoMap);

    // For CompletionMode.none, the results will be enpty list
    Context merge(Context inputCtx, List<Context> results);
    
    Context onInvokeError(Map<String, Context> dispatchedContexts, Exception exception);
    
    Context onTaskError(Context taskCtx, Exception exception);
}
