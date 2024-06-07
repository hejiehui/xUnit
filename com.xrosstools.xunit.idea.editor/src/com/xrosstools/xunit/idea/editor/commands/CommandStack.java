package com.xrosstools.xunit.idea.editor.commands;

import java.util.Stack;

public class CommandStack {
    private static class CommandModelPair {
        Command command;
        Object model;
        CommandModelPair(Command command, Object model) {
            this.command = command;
            this.model = model;
        }
    }

    private Stack<CommandModelPair> undoStack = new Stack<>();
    private Stack<CommandModelPair> redoStack = new Stack<>();

    private Object curModel;

    public void execute(Command command, Object model) {
        command.execute();
        undoStack.push(new CommandModelPair(command, model));
        redoStack.clear();
    }

    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        CommandModelPair pair = undoStack.pop();
        redoStack.push(pair);
        pair.command.undo();
        curModel = pair.model;
        return true;
    }

    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        CommandModelPair pair = redoStack.pop();
        undoStack.push(pair);
        pair.command.redo();
        curModel = pair.model;
        return true;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public String getUndoCommandLabel() {
        return undoStack.peek().command.getLabel();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public String getRedoCommandLabel() {
        return redoStack.peek().command.getLabel();
    }

    public Object getCurModel() {
        return curModel;
    }
}
