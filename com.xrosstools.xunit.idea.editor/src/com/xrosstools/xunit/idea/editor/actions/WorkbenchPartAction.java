package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public abstract class WorkbenchPartAction implements ActionListener {
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

        c.execute();
        listener.propertyChange(null);
    }

    public abstract Command createCommand();
}
