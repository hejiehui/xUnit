package com.xrosstools.xunit.idea.editor.util;

import javax.swing.*;

public class TextPropertyDescriptor extends PropertyDescriptor{
    public TextPropertyDescriptor(Object propertyId, String propertyWasted) {
        setPropertyName(propertyId);
    }

    public JComponent getEditor(Object value) {
        return new JTextField((String)value);
    }
}
