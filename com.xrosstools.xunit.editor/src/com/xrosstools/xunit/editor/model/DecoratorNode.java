package com.xrosstools.xunit.editor.model;

import com.xrosstools.xunit.BehaviorType;

public class DecoratorNode extends CompositeUnitNode {
	// To keep it simple, we don't provide customization for decorator adapter
	// To provide your own decorator adapter implementation, use adapter instead
	private UnitNode startNode;
	private UnitNodePanel unitsPanel = new UnitNodePanel(this, 1);
	private UnitNode endNode;
	
	public DecoratorNode(){
		super("a decorator", StructureType.decorator, true);
	}
	
	public String getDefaultImplName(){
		return DEFAULT_DECORATOR_IMPL;
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
	
	/**
	 * Get from decoratee, if deocratee is null, use default as processor
	 */
	public BehaviorType getType() {
		return getUnit() != null ? getUnit().getType() : BehaviorType.processor;
	}
	
	/**
	 * Do nothing because decorator node type will always be the decoratee type
	 */
	public void setType(BehaviorType type) {
		firePropertyChange(PROP_NODE);
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
