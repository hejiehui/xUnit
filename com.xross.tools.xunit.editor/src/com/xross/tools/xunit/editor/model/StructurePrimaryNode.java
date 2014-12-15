package com.xross.tools.xunit.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xross.tools.xunit.BehaviorType;

public abstract class StructurePrimaryNode extends PrimaryNode {
	public StructurePrimaryNode(String name, BehaviorType type){
		super(name, type);
	}
/*	should not allow user specify structure type. TODO remove structure type
	public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
				getDescriptor(PROP_STRUCTURE_TYPE, StructureType.names),
		};
		
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_STRUCTURE_TYPE.equals(propName))
			return getStructureType().ordinal();

		return super.getPropertyValue(propName);
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_STRUCTURE_TYPE.equals(propName))
			setStructureType(StructureType.getType((Integer)value));
		else
			super.setPropertyValue(propName, value);
	}*/
}
