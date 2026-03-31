package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodeContainerMoveChildCommand extends Command {
	private UnitNodeContainer parent;
	private UnitNode unit;
	private int oldIndex;
	private int index;


	public UnitNodeContainerMoveChildCommand(UnitNodeContainer parent, UnitNode unit, int index){
		this.parent = parent;
		this.unit = unit;
		this.index = index;
		oldIndex = parent.indexOf(unit);
		oldIndex = oldIndex > index ? oldIndex + 1 : oldIndex;
	}
	
	public void execute() {
		parent.move(index, unit);
	}

    public String getLabel() {
        return "Move node in container";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.move(oldIndex, unit);
    }
}