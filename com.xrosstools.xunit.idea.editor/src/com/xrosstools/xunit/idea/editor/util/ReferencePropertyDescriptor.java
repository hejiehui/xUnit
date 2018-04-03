package com.xrosstools.xunit.idea.editor.util;

import com.intellij.openapi.ui.ComboBox;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;

public class ReferencePropertyDescriptor extends PropertyDescriptor {
    private UnitNode node;
    public ReferencePropertyDescriptor(String propertyId, UnitNode node) {
        setId(propertyId);
        this.node = node;
    }

    public JComponent getEditor(Object value) {
        ComboBox ctrl = new ComboBox();
        for(String v: node.getReferenceValues())
            ctrl.addItem(v);

        ctrl.setSelectedItem(node.getReferenceName());
        return ctrl;
    }

    public String getValue(int index) {
        return node.getReferenceName();//node.getReferenceValues()[index];
    }
}
