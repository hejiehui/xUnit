package com.xrosstools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;


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
	
	/**
	 * Do not allow type to be changed for primary type
	 */
	public final void setType(BehaviorType type){}
	
	public boolean isReferenceAllowed() {
	    return true;
	}

}
