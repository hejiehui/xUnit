package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class WorkbenchPartAction implements ActionListener {
    //TODO add Icon
    private String text;

    private boolean checked;
    private PropertyChangeListener listener;

    public void setListener(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Command c = createCommand();
        if(c == null)
            return;

        listener.propertyChange(new PropertyChangeEvent(this, null, null, c));
    }

    public abstract Command createCommand();
}
