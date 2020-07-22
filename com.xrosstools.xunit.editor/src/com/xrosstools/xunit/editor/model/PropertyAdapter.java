package com.xrosstools.xunit.editor.model;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public abstract class PropertyAdapter implements UnitConstants, IPropertySource {
    protected abstract String getCategory(String id);
    
    protected final IPropertyDescriptor getDescriptor(String propName){
        PropertyDescriptor descriptor = new TextPropertyDescriptor(propName, propName);
        descriptor.setCategory(getCategory(propName));
        return descriptor;
    }
    
    protected final IPropertyDescriptor getDescriptor(String propName, String[] values){
        PropertyDescriptor descriptor = new ComboBoxPropertyDescriptor(propName, propName, values);
        descriptor.setCategory(getCategory(propName));
        return descriptor;
    }
    
    protected final IPropertyDescriptor getDescriptor(String propName, Object[] values){
        String[] strValues = new String[values.length];
        int i = 0;
        for(Object o: values)
            strValues[i++] = o.toString();
        return getDescriptor(propName, strValues);
    }
}
