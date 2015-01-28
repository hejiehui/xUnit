package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitNodeProperties;

public class RemoveNodePropertyCommand extends Command {
	private UnitNodeProperties properties;
	private String key;
	private String value; 
	
	public RemoveNodePropertyCommand(
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
