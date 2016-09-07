package com.xrosstools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xunit.editor.model.UnitNodeProperties;

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
