package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class PropertySource implements UnitConstants, IPropertySource {
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener lilistener) {
        listeners.addPropertyChangeListener(lilistener);
    }

    public void removePropertyChangeListener(PropertyChangeListener lilistener) {
        listeners.removePropertyChangeListener(lilistener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue){
        listeners.firePropertyChange(propertyName, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName){
        firePropertyChange(propertyName, null, null);
    }

    @Override
    public PropertyChangeSupport getListeners() {
        return listeners;
    }

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
        for(Object o: values) {
            strValues[i++] = o.toString();
        }
        return getDescriptor(propName, strValues);
    }

    protected String getCategory(String id) {
        return null;
    }
}
