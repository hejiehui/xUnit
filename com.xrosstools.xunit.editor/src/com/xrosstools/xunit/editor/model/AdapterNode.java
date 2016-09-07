package com.xrosstools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;


public class AdapterNode extends CompositeUnitNode {
	private UnitNode startNode;
	private UnitNodePanel unitsPanel = new UnitNodePanel(this, 1);
	private UnitNode endNode;
	
	public AdapterNode(){
		super("an adapter", StructureType.adapter, true);
	}
	
	public AdapterNode(BehaviorType type, UnitNode unit){
		this();
		setType(type);
		setUnit(unit);
	}
	
	public String getDefaultImplName(){
		return DEFAULT_ADAPTER_IMPL;
	}

	protected String getCategory(String id) {
		return null;
	}

	public void setUnit(UnitNode unit) {
		unitsPanel.set(INDEX_UNIT, unit);
	}
	
	public UnitNode getUnit() {
		return  unitsPanel.get(INDEX_UNIT);
	}
	
    public UnitNode getStartNode(){
    	return startNode;
    }
    public UnitNodeContainer getContainerNode(){
    	return unitsPanel;
    }
    public UnitNode getEndNode(){
    	return endNode;
    }
}
