package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class RenamePropertyCommand extends Command {
	private UnitNodeProperties properties;
	private String newName;
	private String oldName;
	private boolean executed;
	
	public RenamePropertyCommand(
			UnitNodeProperties properties, 
			String oldName,
			String newName){
		this.properties = properties;
		this.oldName = oldName;
		this.newName = newName;
	}
	
	public void execute() {
		if(!properties.isValidId(newName)){
			executed = false;
			return;
		}
		properties.renameProperty(oldName, newName);
		executed = true;
	}
	
    public String getLabel() {
        return "Rename property";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		properties.renameProperty(newName, oldName);
    }
}
