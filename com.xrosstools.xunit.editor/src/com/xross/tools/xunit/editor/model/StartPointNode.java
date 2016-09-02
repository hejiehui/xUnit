package com.xross.tools.xunit.editor.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xross.tools.xunit.editor.Activator;
import com.xrosstools.xunit.BehaviorType;

public class StartPointNode extends IconNode{
	public StartPointNode(){
		super(BehaviorType.processor, Activator.START_POINT, false);
	}
	
	/**
	 * No need for properties for panel
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0];
	}
}
