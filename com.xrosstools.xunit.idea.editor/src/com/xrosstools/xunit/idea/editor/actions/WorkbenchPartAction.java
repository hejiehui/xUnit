package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.command.CommandProcessor;
import com.xrosstools.xunit.idea.editor.commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class WorkbenchPartAction implements ActionListener {
    private String text;

    private boolean checked;

    public boolean isChecked() {
        return checked;
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

    protected boolean calculateEnabled() {
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        run();
    }

    public abstract void run();

    public void execute(Command command) {
//        CommandProcessor.getInstance().executeCommand()
        command.execute();
    }
}
