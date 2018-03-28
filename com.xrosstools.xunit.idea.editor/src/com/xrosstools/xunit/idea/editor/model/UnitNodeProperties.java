package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.IPropertySource;
import com.xrosstools.xunit.idea.editor.util.PropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.TextPropertyDescriptor;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class UnitNodeProperties implements UnitConstants, IPropertySource {
    private Map<String, String> properties = new LinkedHashMap<String, String>();

    public boolean contains(String propName) {
        return properties.containsKey(propName);
    }

    public Set<String> getNames(){
        return properties.keySet();
    }

    public String getProperty(String name) {
        return properties.get(name);
    }

    public Object getEditableValue() {
        return this;
    }

    public boolean isValidId(String key){
        if(key == null || key.trim().length() == 0)
            return false;

        return true;
    }

    @Override
    public PropertyChangeSupport getListeners() {
        throw new IllegalStateException("not supported");
    }

    public void addProperty(String name, String value){
        properties.put(name, value);
    }

    public void renameProperty(String oldName, String newName){
        properties.put(newName, properties.remove(oldName));
    }

    public String removeProperty(String name){
        return properties.remove(name);
    }

    public void setPropertyValue(String id, Object value) {
        properties.put(id, (String)value);
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> descciptors = new ArrayList<IPropertyDescriptor>();

        for(String name: properties.keySet()){
            IPropertyDescriptor descriptor = new TextPropertyDescriptor(new PropertyId(name));
            descriptor.setCategory("Properties");
            descciptors.add(descriptor);
        }
        return descciptors.toArray(new IPropertyDescriptor[0]);
    }

    public Object getPropertyValue(Object id) {
        if(id instanceof PropertyId)
            return properties.get(((PropertyId)id).name);

        return null;
    }

    public boolean isPropertySet(Object id) {
        return true;
    }

    public void resetPropertyValue(Object id) {
    }

    public void setPropertyValue(Object id, Object value) {
        if(id instanceof PropertyId)
            properties.put(((PropertyId)id).name, (String)value);
    }

    private class PropertyId {
        String name;
        PropertyId(String name) {this.name = name;}
    }
}
