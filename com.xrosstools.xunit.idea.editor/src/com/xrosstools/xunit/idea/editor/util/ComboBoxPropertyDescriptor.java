package com.xrosstools.xunit.idea.editor.util;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxPropertyDescriptor extends PropertyDescriptor {
    private String[] values;
    private ComboBox ctrl = new ComboBox();
    public ComboBoxPropertyDescriptor(String propertyId, String label, String[] values) {
        setId(propertyId);
        this.values = values;
        ctrl.setBorder(null);
    }

    public JComponent getEditor(Object value) {
        ctrl.removeAllItems();
        for(String v: values)
            ctrl.addItem(v);

        ctrl.setSelectedIndex((Integer) value);
        ctrl.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                    ctrl.transferFocusUpCycle();
            }
        });

        return ctrl;
    }

    public String getValue(int index) {
        return values[index];
    }
}
