package com.xrosstools.xunit.def;

import java.util.concurrent.TimeUnit;

import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.Unit;

public class DispatcherDef extends UnitDef{
    private CompletionMode completionMode;
    private long timeout;
    private TimeUnit timeUnit;
    
    public CompletionMode getCompletionMode() {
        return completionMode;
    }

    public void setCompletionMode(CompletionMode completionMode) {
        this.completionMode = completionMode;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    protected Unit createInstance() throws Exception{
        Dispatcher dispatcher = (Dispatcher)super.createInstance();
        
        dispatcher.setCompletionMode(completionMode);
        dispatcher.setTimeout(timeout);
        dispatcher.setTimeUnit(timeUnit);
        
        return dispatcher;
    }
}
