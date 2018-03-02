package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeContainerMoveChildCommand extends Command {
	private UnitNodeContainer parent;
	private UnitNode unit;
	private int oldIndex;
	private int index;
	
	public UnitNodeContainerMoveChildCommand(UnitNodeContainer parent, UnitNode unit, int index){
		this.parent = parent;
		this.unit = unit;
		oldIndex = parent.indexOf(unit);
		this.index = index;
	}
	
	public void execute() {
		parent.move(index, unit);
	}

    public String getLabel() {
        return "Move Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.move(oldIndex, unit);
    }
}