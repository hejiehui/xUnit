package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.IPropertySource;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class PropertySource implements UnitConstants, IPropertySource {
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(UnitNodeConnection lilistener) {
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
}
