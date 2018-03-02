package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;

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
