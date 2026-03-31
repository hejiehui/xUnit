package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.idea.gef.util.IPropertyDescriptor;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class UnitNodeProperties implements UnitConstants, IPropertySource {
    private Map<String, String> properties = new LinkedHashMap<>();

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
        return key != null && key.trim().length() > 0;
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
            IPropertyDescriptor descriptor = new TextPropertyDescriptor(name);
            descriptor.setCategory("Properties");
            descciptors.add(descriptor);
        }
        return descciptors.toArray(new IPropertyDescriptor[0]);
    }

    public Object getPropertyValue(Object id) {
        return properties.get((String)id);
    }

    public boolean isPropertySet(Object id) {
        return true;
    }

    public void resetPropertyValue(Object id) {
    }

    public void setPropertyValue(Object id, Object value) {
        properties.put((String)id, (String)value);
    }
}
