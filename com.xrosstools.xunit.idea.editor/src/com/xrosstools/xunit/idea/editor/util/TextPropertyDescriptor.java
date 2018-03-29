package com.xrosstools.xunit.idea.editor.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextPropertyDescriptor extends PropertyDescriptor{
    private JTextField editor = new JTextField();

    public TextPropertyDescriptor(Object propertyId) {
        setId(propertyId);
        editor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyCode() == e.VK_ENTER || e.getKeyChar() == '\n')
                    editor.getParent().transferFocus();
            }
        });
    }

    public JComponent getEditor(Object value) {
        editor.setText((String)value);
        return editor;
    }
}
