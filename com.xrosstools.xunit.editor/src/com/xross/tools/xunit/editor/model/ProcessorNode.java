package com.xross.tools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;

public class ProcessorNode extends PrimaryNode {
	public ProcessorNode(){
		this(BehaviorType.processor.name());
	}

	public ProcessorNode(String name){
		super(name, BehaviorType.processor);
	}
}
