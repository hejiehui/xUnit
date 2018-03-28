package com.xrosstools.xunit.idea.editor.util;

import javax.swing.*;

public interface IPropertyDescriptor {
    void setPropertyName(Object propertyName);
    String getPropertyName();

    void setCategory(String category);
    String getCategory();

    JComponent getEditor(Object value);

    String getValue(int index);
}
