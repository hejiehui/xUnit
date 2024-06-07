package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.BaseLoopNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class DeleteNodeCommand extends Command {
    private Object parent;
    private UnitNode unit;
    int index = -1;
    boolean removed = false;

    private UnitNodeConnection oldInput;
    private UnitNodeConnection oldOutput;

    public DeleteNodeCommand(Object parent, UnitNode unit){
        this.parent = parent;
        this.unit = unit;

        this.oldInput = unit.getInput();
        this.oldOutput = unit.getOutput();
    }
    public void execute() {
        if(parent instanceof UnitNodeContainer){
            deleteFromContainer();
        }
        if(parent instanceof BaseLoopNode){
            deleteFromLoop();
        }
    }

    public String getLabel() {
        return "Delete node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
        if(parent instanceof UnitNodeContainer){
            addToContainer();
        }
        if(parent instanceof BaseLoopNode){
            addToLoop();
        }

        UnitNodeConnection.restoreConnections(oldInput, unit, oldOutput);
    }

    private void deleteFromContainer(){
        index = ((UnitNodeContainer)parent).indexOf(unit);
        ((UnitNodeContainer)parent).remove(unit);
    }

    private void addToContainer(){
        ((UnitNodeContainer)parent).add(index, unit);
    }

    private void deleteFromLoop(){
        BaseLoopNode loop = (BaseLoopNode)parent;
        if(unit == loop.getUnit()){
            loop.setUnit(null);
            removed = true;
        }else
            removed = false;
    }

    private void addToLoop(){
        if(removed)
            ((BaseLoopNode)parent).setUnit(unit);
    }

}
