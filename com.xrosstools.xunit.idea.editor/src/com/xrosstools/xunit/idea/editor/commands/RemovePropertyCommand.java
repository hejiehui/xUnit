package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class RemovePropertyCommand extends Command {
	private UnitNodeProperties properties;
	private String key;
	private String value; 
	
	public RemovePropertyCommand(
			UnitNodeProperties properties, 
			String key){
		this.properties = properties;
		this.key = key;
	}
	
	public void execute() {
		value = properties.removeProperty(key);
	}
	
    public String getLabel() {
        return "Remove property";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	properties.addProperty(key, value);
    }
}
