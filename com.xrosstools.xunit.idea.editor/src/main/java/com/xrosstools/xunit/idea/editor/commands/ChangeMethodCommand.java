package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class ChangeMethodCommand extends Command {
    private UnitNode node;
    private String oldValue;
    private String newValue;

    public ChangeMethodCommand(UnitNode node, String newValue) {
        this.node= node;
        this.oldValue = node.getMethodName();
        this.newValue = newValue;
    }

    public String getLabel() {
        return "Change method";
    }

    public void execute() {
        node.setMethodName(newValue);
    }

    public void redo() {
        execute();
    }

    public void undo() {
        node.setMethodName(oldValue);
    }
}
