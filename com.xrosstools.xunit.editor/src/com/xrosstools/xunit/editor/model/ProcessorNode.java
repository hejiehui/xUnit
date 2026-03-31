package com.xrosstools.xunit.editor.model;

public class ProcessorNode extends PrimaryNode {
	public ProcessorNode(){
		this(BehaviorType.processor.name());
	}

	public ProcessorNode(String name){
		super(name, BehaviorType.processor);
	}
}
