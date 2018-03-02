package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.BaseLoopNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class DeleteNodeCommand extends Command {
    @Override
    public void run() {
        execute();
    }

    private Object parent;
    private UnitNode unit;
    int index = -1;
    boolean removed = false;

    public DeleteNodeCommand(Object parent, UnitNode unit){
        this.parent = parent;
        this.unit = unit;
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
        return "delete node";
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
