package com.xrosstools.xunit.idea.editor.commands;


import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeContainerCreateCommand extends Command {
	private UnitNodeContainer parent;
	private UnitNode unit;
	private int index;
	
	public UnitNodeContainerCreateCommand(UnitNodeContainer parent, UnitNode unit, int index){
		this.parent = parent;
		this.unit = unit;
		this.index = index;
	}
	
	public void execute() {
		parent.add(index, unit);
	}

    public String getLabel() {
        return "Create Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.remove(unit);
    }
}