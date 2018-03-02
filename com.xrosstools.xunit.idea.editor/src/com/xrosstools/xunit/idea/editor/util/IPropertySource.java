package com.xrosstools.xunit.idea.editor.util;

import java.beans.PropertyChangeSupport;

public interface IPropertySource {
    PropertyChangeSupport getListeners();
    Object getEditableValue();
    IPropertyDescriptor[] getPropertyDescriptors();
    Object getPropertyValue(Object name);
    boolean isPropertySet(Object id);
    void resetPropertyValue(Object id);
    void setPropertyValue(Object name, Object value);
}
