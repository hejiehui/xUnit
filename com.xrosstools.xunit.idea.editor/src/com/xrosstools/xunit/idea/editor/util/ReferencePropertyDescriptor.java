package com.xrosstools.xunit.idea.editor.util;

import com.intellij.openapi.ui.ComboBox;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ReferencePropertyDescriptor extends PropertyDescriptor {
    private UnitNode node;
    private ComboBox ctrl = new ComboBox();
    public ReferencePropertyDescriptor(String propertyId, UnitNode node) {
        setId(propertyId);
        this.node = node;
        ctrl.setBorder(null);
    }

    public JComponent getEditor(Object value) {
        ctrl.removeAllItems();
        for(String v: node.getReferenceValues())
            ctrl.addItem(v);

        ctrl.setSelectedItem(node.getReferenceName());
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
        return node.getReferenceName();//node.getReferenceValues()[index];
    }
}
