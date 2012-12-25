package com.xross.tools.xunit.editor.model;

import com.xross.tools.xunit.BehaviorType;

public class ConverterNode extends StructurePrimaryNode {
	public ConverterNode(){
		this(BehaviorType.converter.name());
	}

	public ConverterNode(String name){
		super(name, BehaviorType.converter);
	}
}
