package com.xrosstools.xunit.idea.editor.util;

public abstract class PropertyDescriptor implements IPropertyDescriptor {
    private String category;
    private String propertyName;

    @Override
    public void setPropertyName(Object propertyName) {
        this.propertyName = (String) propertyName;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }
}
