package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeContainerAddCommand extends Command {
	private UnitNodeContainer oldParent;
	private int oldIndex;
	private UnitNodeContainer newParent;
	private UnitNode unit;
	private int index;

	private UnitNodeConnection oldInput;
	private UnitNodeConnection oldOutput;

	private UnitNodeConnection newInput;
	private UnitNodeConnection newOutput;

	public UnitNodeContainerAddCommand(UnitNodeContainer oldParent, UnitNodeContainer newParent, UnitNode unit, int index){
		this.oldParent = oldParent;
		this.oldIndex = oldParent.indexOf(unit);
		this.newParent = newParent;
		this.unit = unit;
		this.index = index;

		this.oldInput = unit.getInput();
		this.oldOutput = unit.getOutput();
	}
	
	public void execute() {
		oldParent.remove(unit);
		newParent.add(index, unit);

		if(newInput == null) {
			this.newInput = unit.getInput();
			this.newOutput = unit.getOutput();
		} else {
			UnitNodeConnection.restoreConnections(newInput, unit, newOutput);
		}
	}

    public String getLabel() {
        return "Add node in container";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	newParent.remove(unit);
    	oldParent.add(oldIndex, unit);
		UnitNodeConnection.restoreConnections(oldInput, unit, oldOutput);
    }
}