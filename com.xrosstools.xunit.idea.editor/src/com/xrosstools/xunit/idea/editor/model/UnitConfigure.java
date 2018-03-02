package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.IPropertySource;
import com.xrosstools.xunit.idea.editor.util.TextPropertyDescriptor;

import java.beans.PropertyChangeSupport;
import java.util.*;

public class UnitConfigure implements UnitConstants, IPropertySource {
    private static class ConfigId {
        String category;
        String key;
        ConfigId(String category, String key){
            this.category = category;
            this.key = key;
        }
    }

    // The structure is category-->property
    private Map<String, Map<String, String>> categories = new LinkedHashMap<String, Map<String, String>>();

    public boolean isSimple(){
        return categories.size() == 1 && categories.containsKey(DEFAULT_CATEGORY);
    }

    public boolean containsCategory(String catName) {
        return categories.containsKey(catName);
    }

    public Set<String> getCategoryNames(){
        return categories.keySet();
    }

    public Set<String> getEntryNames(String catName){
        return categories.get(catName).keySet();
    }

    public Map<String, String> getCategory(String catName) {
        return categories.get(catName);
    }

    public Object getEditableValue() {
        return this;
    }

    public boolean isValidId(String key){
        if(key == null || key.trim().length() == 0)
            return false;

        return true;
    }

    public void addCategory(String name, Map<String, String> value){
        categories.put(name, value);
    }

    public void addCategory(String catName){
        categories.put(catName, new LinkedHashMap<String, String>());
    }

    public void renameCategory(String oldName, String newName){
        categories.put(newName, categories.remove(oldName));
    }

    public Map<String, String> removeCategory(String catName){
        return categories.remove(catName);
    }

    public String getEntry(String catName, String key){
        return categories.get(catName).get(key);
    }

    public void addEntry(String catName, String key){
        addEntry(catName, key, "");
    }

    public void addEntry(String catName, String key, String value){
        categories.get(catName).put(key, value);
    }

    public void renameEntry(String catName, String oldName, String newName){
        categories.get(catName).put(newName, categories.get(catName).remove(oldName));
    }

    public String removeEntry(String catName, String key){
        return categories.get(catName).remove(key);
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> descciptors = new ArrayList<IPropertyDescriptor>();
        boolean simple = isSimple();

        for(Map.Entry<String, Map<String, String>> category: categories.entrySet()){
            String catName = category.getKey();
            for(Map.Entry<String, String> entry: category.getValue().entrySet()){
                ConfigId id = new ConfigId(catName, entry.getKey());
                IPropertyDescriptor descriptor = new TextPropertyDescriptor(id, entry.getKey());
                descriptor.setCategory(simple? "Properties" : "Properties " + catName);
                descciptors.add(descriptor);
            }
        }
        return descciptors.toArray(new IPropertyDescriptor[0]);
    }

    public Object getPropertyValue(Object idObj) {
        ConfigId id = (ConfigId)idObj;
        return categories.get(id.category).get(id.key);
    }

    public boolean isPropertySet(Object id) {
        return true;
    }

    public void resetPropertyValue(Object id) {
    }

    public void setPropertyValue(Object idObj, Object value) {
        ConfigId id = (ConfigId)idObj;

        categories.get(id.category).put(id.key, (String)value);
    }

    @Override
    public PropertyChangeSupport getListeners() {
        throw new IllegalStateException("not supported");
    }
}
