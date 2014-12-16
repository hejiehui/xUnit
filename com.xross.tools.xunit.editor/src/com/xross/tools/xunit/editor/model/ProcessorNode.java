package com.xross.tools.xunit.editor.model;

import com.xross.tools.xunit.BehaviorType;

public class ProcessorNode extends PrimaryNode {
	public ProcessorNode(){
		this(BehaviorType.processor.name());
	}

	public ProcessorNode(String name){
		super(name, BehaviorType.processor);
	}
}
