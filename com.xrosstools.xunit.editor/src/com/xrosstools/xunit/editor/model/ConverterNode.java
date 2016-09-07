package com.xrosstools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;

public class ConverterNode extends PrimaryNode {
	public ConverterNode(){
		this(BehaviorType.converter.name());
	}

	public ConverterNode(String name){
		super(name, BehaviorType.converter);
	}
}
