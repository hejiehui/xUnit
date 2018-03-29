package com.xrosstools.xunit.idea.editor.util;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;

public class ComboBoxPropertyDescriptor extends PropertyDescriptor {
    private String[] values;
    public ComboBoxPropertyDescriptor(String propertyId, String label, String[] values) {
        setId(propertyId);
        this.values = values;
    }

    public JComponent getEditor(Object value) {
        ComboBox ctrl = new ComboBox();
        for(String v: values)
            ctrl.addItem(v);

        ctrl.setSelectedItem(value);
        return ctrl;
    }

    public String getValue(int index) {
        return values[index];
    }
}
