package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitConfigure;

public class AddCategoryCommand extends Command {
	private UnitConfigure configure;
	private String catName;
	private boolean executed;
	
	public AddCategoryCommand(
			UnitConfigure configure, 
			String catName){
		this.configure = configure;
		this.catName = catName;
	}
	
	public void execute() {
		if(!configure.isValidId(catName) || configure.getCategory(catName) != null){
			executed = false;
			return;
		}
		configure.addCategory(catName);
		configure.addEntry(catName, "key");
		executed = true;
	}
	
    public String getLabel() {
        return "Add category";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		configure.removeCategory(catName);
    }
}
