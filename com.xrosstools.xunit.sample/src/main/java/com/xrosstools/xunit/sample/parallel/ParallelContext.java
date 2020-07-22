package com.xrosstools.xunit.sample.parallel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xrosstools.xunit.Context;

public class ParallelContext implements Context {
    public Map<String, TaskContext> taskCtxMap = new HashMap<>();
    public boolean fail;
    public List<String> results = new ArrayList<>();
    public Exception exception;
    
    public void add(TaskContext ctx) {
        taskCtxMap.put(ctx.taskId, ctx);
    }
    
    public Set<String> sucessTasks = new HashSet<>();
    public Set<String> failedTasks = new HashSet<>();
}
