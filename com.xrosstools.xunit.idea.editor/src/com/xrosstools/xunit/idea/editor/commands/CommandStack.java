package com.xrosstools.xunit.idea.editor.commands;

import java.util.Stack;

public class CommandStack {

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        Command command = undoStack.pop();
        redoStack.push(command);
        command.undo();
        return true;
    }

    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        Command command = redoStack.pop();
        undoStack.push(command);
        command.redo();
        return true;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}
