package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameEntryCommand extends Command {
	private UnitConfigure configure;
	private String catName;
	private String oldName;
	private String newName;
	private boolean executed;
	
	public RenameEntryCommand(
			UnitConfigure configure, 
			String catName,
			String oldName,
			String newName){
		this.configure = configure;
		this.catName = catName;
		this.oldName = oldName;
		this.newName = newName;
	}
	
	public void execute() {
		if(!configure.isValidId(newName)){
			executed = false;
			return;
		}
		configure.renameEntry(catName, oldName, newName);
		executed = true;
	}
	
    public String getLabel() {
        return "Rename entry";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		configure.renameEntry(catName, newName, oldName);
    }
}
