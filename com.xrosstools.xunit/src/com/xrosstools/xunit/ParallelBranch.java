package com.xrosstools.xunit;

public interface ParallelBranch extends Unit{
    void setDispatcher(Dispatcher dispatcher);
    
    void add(String taskId, TaskType type, Unit unit);

    void validate();
}
