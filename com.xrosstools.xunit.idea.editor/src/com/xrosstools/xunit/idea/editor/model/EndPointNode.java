package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;

public class EndPointNode extends IconNode{
    public EndPointNode(){
        super(BehaviorType.processor, Activator.END_POINT, false);
    }

    /**
     * No need for properties for panel
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[0];
    }
}
