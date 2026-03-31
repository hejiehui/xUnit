package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitConfigure;

public class AddEntryCommand extends Command {
	private UnitConfigure configure;
	private String catName;
	private String key;
	private boolean executed;
	
	public AddEntryCommand(
			UnitConfigure configure, 
			String catName,
			String key){
		this.configure = configure;
		this.catName = catName;
		this.key = key;
	}
	
	public void execute() {
		if(!configure.isValidId(key) || configure.getCategory(catName).containsKey(key)){
			executed = false;
			return;
		}
		configure.addEntry(catName, key);
		executed = true;
	}
	
    public String getLabel() {
        return "Add entry";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		configure.removeEntry(catName, key);
    }
}
