package com.xrosstools.xunit.idea.editor.model;

public class ProcessorNode extends PrimaryNode {
    public ProcessorNode(){
        this(BehaviorType.processor.name());
    }

    public ProcessorNode(String name){
        super(name, BehaviorType.processor);
    }
}
