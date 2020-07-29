package com.xrosstools.xunit;

public class UnitDefinition {
    private String name;
    private BehaviorType type;
    private String description;
    private boolean singleton;
    private String className;
    private String key;
    private TaskType taskType;
    private String validLabel;
    private String invalidLabel;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BehaviorType getType() {
        return type;
    }
    public void setType(BehaviorType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isSingleton() {
        return singleton;
    }
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public TaskType getTaskType() {
        return taskType;
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public String getValidLabel() {
        return validLabel;
    }
    public void setValidLabel(String validLabel) {
        this.validLabel = validLabel;
    }
    public String getInvalidLabel() {
        return invalidLabel;
    }
    public void setInvalidLabel(String invalidLabel) {
        this.invalidLabel = invalidLabel;
    }
}
