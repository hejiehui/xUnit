package com.xross.tools.xunit.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xross.tools.xunit.BehaviorType;
import com.xross.tools.xunit.editor.Activator;

public class ValidatorNode extends IconNode {
	private String validLable = DEFAULT_VALID_LABEL;
	private String invalidLabel = DEFAULT_INVALID_LABEL;
	
	public ValidatorNode(){
		super(BehaviorType.validator, Activator.VALIDATOR, true);
	}
	
	protected String getCategory(String id){
		return id == PROP_VALID_LABEL || id == PROP_INVALID_LABEL? CATEGORY_OPTIONAL : null;
	}
	
	public void addOutput(UnitNodeConnection output){
		super.addOutput(output);
	}
	
	public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
				getDescriptor(PROP_VALID_LABEL),
				getDescriptor(PROP_INVALID_LABEL),
		};
		
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_VALID_LABEL.equals(propName))
			return getValidLabel();
		if (PROP_INVALID_LABEL.equals(propName))
			return getInvalidLabel();

		return super.getPropertyValue(propName);
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_VALID_LABEL.equals(propName))
			setValidLabel((String)value);
		else if (PROP_INVALID_LABEL.equals(propName))
			setInvalidLabel((String)value);
		else
			super.setPropertyValue(propName, value);
	}

	public String getValidLabel() {
		return validLable;
	}
	
	public void setValidLabel(String validLable) {
		this.validLable = validLable;
		firePropertyChange(PROP_NODE);
	}
	
	public String getInvalidLabel() {
		return invalidLabel;
	}
	
	public void setInvalidLabel(String invalidLabel) {
		this.invalidLabel = invalidLabel;
		firePropertyChange(PROP_NODE);
	}
}
