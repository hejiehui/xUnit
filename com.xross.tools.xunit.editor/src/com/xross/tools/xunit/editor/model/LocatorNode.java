package com.xross.tools.xunit.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xross.tools.xunit.BehaviorType;
import com.xross.tools.xunit.editor.Activator;

public class LocatorNode extends IconNode{
	private String defaultKey;
	
	public LocatorNode(){
		super(BehaviorType.locator, Activator.LOCATOR, true);
	}
	
	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
		firePropertyChange(PROP_NODE);
	}
	
	public IPropertyDescriptor[] getAdditionalPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
				getDescriptor(PROP_DEFAULT_KEY),
		};
		
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_DEFAULT_KEY.equals(propName))
			return helper.getValue(defaultKey);

		return super.getPropertyValue(propName);
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_DEFAULT_KEY.equals(propName))
			setDefaultKey((String)value);
		else
			super.setPropertyValue(propName, value);
	}
}
