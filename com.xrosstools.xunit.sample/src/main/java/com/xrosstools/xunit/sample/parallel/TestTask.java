package com.xrosstools.xunit.sample.parallel;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class TestTask implements Processor {
    @Override
    public void process(Context ctx) {
        TaskContext taskCtx = (TaskContext)ctx;
        
        if(taskCtx.fail)
            throw new RuntimeException(taskCtx.taskId + " failed");
        
        try {
            Thread.sleep(taskCtx.timeout);
            taskCtx.result.set(Thread.currentThread().getId() + " running " + taskCtx.taskId);
            taskCtx.success.set(true);
        } catch (InterruptedException e) {
            throw new RuntimeException(taskCtx.taskId + " is interrupted");
        }
    }
}
