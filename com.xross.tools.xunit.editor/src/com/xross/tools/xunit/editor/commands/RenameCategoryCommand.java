package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameCategoryCommand extends Command {
	private UnitConfigure configure;
	private String oldName;
	private String newName;
	private boolean executed;
	
	public RenameCategoryCommand(
			UnitConfigure configure, 
			String oldName,
			String newName){
		this.configure = configure;
		this.oldName = oldName;
		this.newName = newName;
	}
	
	public void execute() {
		if(!configure.isValidId(newName)){
			executed = false;
			return;
		}
		configure.renameCategory(oldName, newName);
		executed = true;
	}
	
    public String getLabel() {
        return "Rename category";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		configure.renameCategory(newName, oldName);
    }
}
