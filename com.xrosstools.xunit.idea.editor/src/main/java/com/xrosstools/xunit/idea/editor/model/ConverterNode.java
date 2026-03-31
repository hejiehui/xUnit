package com.xrosstools.xunit.idea.editor.model;

public class ConverterNode extends PrimaryNode {
    public ConverterNode(){
        this(BehaviorType.converter.name());
    }

    public ConverterNode(String name){
        super(name, BehaviorType.converter);
    }
}
