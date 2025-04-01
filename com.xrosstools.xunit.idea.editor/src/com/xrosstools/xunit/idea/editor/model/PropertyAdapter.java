package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.idea.gef.util.*;

import java.beans.PropertyChangeListener;

public abstract class PropertyAdapter extends PropertySource implements UnitConstants{
    protected abstract String getCategory(String id);
    
    protected final IPropertyDescriptor getDescriptor(String propName){
        PropertyDescriptor descriptor = new TextPropertyDescriptor(propName);
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

    public void addPropertyChangeListener(PropertyChangeListener lilistener) {
        getListeners().addPropertyChangeListener(lilistener);
    }

    public void removePropertyChangeListener(PropertyChangeListener lilistener) {
        getListeners().removePropertyChangeListener(lilistener);
    }
}
