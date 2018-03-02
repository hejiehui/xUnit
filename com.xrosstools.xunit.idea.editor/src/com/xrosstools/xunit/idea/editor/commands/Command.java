package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitConstants;

public abstract class Command implements Runnable, UnitConstants {
    @Override
    public void run() {
        execute();
    }

    public abstract void execute();

    public abstract String  getLabel();

    public void redo() {
        execute();
    }

    public abstract void undo();
}
