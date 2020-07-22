package com.xrosstools.xunit.sample.parallel;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.xrosstools.xunit.Context;

public class TaskContext  implements Context {
    public String taskId;
    public long timeout = 1;
    // If the execution fails at the end
    public boolean fail = false;

    // The following two attributes will be modified in different thread, so them are wrapped in automic value;
    // If the final result
    public AtomicBoolean success = new AtomicBoolean(false);
    
    public AtomicReference<String> result = new AtomicReference<>();
}
