package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeContainerAddCommand extends Command {
	private UnitNodeContainer oldParent;
	private int oldIndex;
	private UnitNodeContainer newParent;
	private UnitNode unit;
	private int index;
	
	public UnitNodeContainerAddCommand(UnitNodeContainer oldParent, UnitNodeContainer newParent, UnitNode unit, int index){
		this.oldParent = oldParent;
		this.oldIndex = oldParent.indexOf(unit);
		this.newParent = newParent;
		this.unit = unit;
		this.index = index;
	}
	
	public void execute() {
		oldParent.remove(unit);
		newParent.add(index, unit);
	}

    public String getLabel() {
        return "Add Node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	newParent.remove(unit);
    	oldParent.add(oldIndex, unit);
    }
}