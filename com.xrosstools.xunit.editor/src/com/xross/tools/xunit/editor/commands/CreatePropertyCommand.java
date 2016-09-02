package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitNodeProperties;

public class CreatePropertyCommand extends Command {
	private UnitNodeProperties properties;
	private String key;
	private boolean executed;
	
	public CreatePropertyCommand(
			UnitNodeProperties properties, 
			String key){
		this.properties = properties;
		this.key = key;
	}
	
	public void execute() {
		if(!properties.isValidId(key) || properties.contains(key)){
			executed = false;
			return;
		}
		properties.addProperty(key, "");
		executed = true;
	}
	
    public String getLabel() {
        return "Add property";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		properties.removeProperty(key);
    }
}
