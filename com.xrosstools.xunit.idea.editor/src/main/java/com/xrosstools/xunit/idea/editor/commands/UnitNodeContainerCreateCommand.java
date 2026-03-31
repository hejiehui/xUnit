package com.xrosstools.xunit.idea.editor.commands;


import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeContainerCreateCommand extends Command {
	private UnitNodeContainer parent;
	private UnitNode unit;
	private int index;

	private UnitNodeConnection newInput;
	private UnitNodeConnection newOutput;

	public UnitNodeContainerCreateCommand(UnitNodeContainer parent, UnitNode unit, int index){
		this.parent = parent;
		this.unit = unit;
		this.index = index;
	}
	
	public void execute() {
		parent.add(index, unit);

		if(newInput == null) {
			this.newInput = unit.getInput();
			this.newOutput = unit.getOutput();
		} else {
			UnitNodeConnection.restoreConnections(newInput, unit, newOutput);
		}
	}

    public String getLabel() {
        return "Create node in container";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	parent.remove(unit);
    }
}