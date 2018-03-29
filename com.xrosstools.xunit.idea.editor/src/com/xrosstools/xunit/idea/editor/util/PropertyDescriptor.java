package com.xrosstools.xunit.idea.editor.util;

public abstract class PropertyDescriptor implements IPropertyDescriptor {
    private String category;
    private Object id;

    @Override
    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public String getValue(int index) {
        return null;
    }
}
