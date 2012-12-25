package com.xross.tools.xunit.editor.commands;

import java.util.Map;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RemoveCategoryCommand extends Command {
	private UnitConfigure configure;
	private String catName;
	private Map<String, String> entries;
	private boolean executed;
	
	public RemoveCategoryCommand(
			UnitConfigure configure, 
			String catName){
		this.configure = configure;
		this.catName = catName;
	}
	
	public void execute() {
		if(!configure.containsCategory(catName)){
			executed = false;
			return;
		}
		
		entries = configure.removeCategory(catName);
		executed = true;
	}
	
    public String getLabel() {
        return "Remove category";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		configure.addCategory(catName, entries);
    }
}
