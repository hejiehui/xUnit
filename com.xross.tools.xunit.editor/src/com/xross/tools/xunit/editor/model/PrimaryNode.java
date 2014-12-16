package com.xross.tools.xunit.editor.model;

import com.xross.tools.xunit.BehaviorType;


public abstract class PrimaryNode extends UnitNode {
	public PrimaryNode(BehaviorType type){
		this(type.name(), type);
	}
	
	public PrimaryNode(String name, BehaviorType type){
		super(name);
		super.setType(type);
	}
	
	public String getDefaultImplName(){
		return DEFAULT_PRIMARY_IMPL;
	}

	protected String getCategory(String id){
		return null;
	}
	
	public String[] getReferenceValues(){
		return helper.getReferenceNames(getType(), getName());
	}
	
	/**
	 * Do not allow type to be changed for primary type
	 */
	public final void setType(BehaviorType type){}
}
