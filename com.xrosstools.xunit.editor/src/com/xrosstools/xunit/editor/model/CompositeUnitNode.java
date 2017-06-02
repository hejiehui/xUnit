package com.xrosstools.xunit.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xrosstools.xunit.BehaviorType;


/**
 * TODO The reference property should not be set 
 * @author jiehe
 *
 */
public abstract class CompositeUnitNode extends UnitNode {
	private boolean vertical;
	private StructureType structureType = StructureType.primary;
	
	public CompositeUnitNode(String name, StructureType structureType){
		this(name, structureType, false);
	}
	
	public CompositeUnitNode(String name, StructureType structureType, boolean vertical){
		super(name);
		this.vertical = vertical;
		this.structureType = structureType;
	}
	
	public StructureType getStructureType() {
		return structureType;
	}

	public boolean isVertical() {
		return vertical;
	}
	
	protected final UnitNode createSampleNode(String name){
		return new ProcessorNode(name);
	}
	
	public boolean isReferenceAllowed() {
	    return false;
	}
	
	public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
				getDescriptor(PROP_BEHAVIOR_TYPE, BehaviorType.names),
		};
		
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_BEHAVIOR_TYPE.equals(propName))
			return getType().ordinal();

		return super.getPropertyValue(propName);
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_BEHAVIOR_TYPE.equals(propName))
			setType(BehaviorType.getType((Integer)value));
		else
			super.setPropertyValue(propName, value);
	}

	public abstract UnitNode getStartNode();
	public abstract UnitNodeContainer getContainerNode();
	public abstract UnitNode getEndNode();
	
	public void reconnect(){
		
	}
	
	public void unitSet(int index, UnitNode unit){
		reconnect();
	}
	
	public void unitAdded(int index, UnitNode unit){
		reconnect();
	}
	
	public void unitRemoved(UnitNode unit){
		reconnect();
	}
	
	public void unitMoved(UnitNode unit){
		reconnect();
	}
}
