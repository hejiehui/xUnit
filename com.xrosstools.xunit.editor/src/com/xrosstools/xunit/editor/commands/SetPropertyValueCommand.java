package com.xrosstools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xunit.editor.model.UnitNodeProperties;

public class SetPropertyValueCommand extends Command {
	private UnitNodeProperties properties;
	private String key;
	private String oldValue;
	private String value;
	
	public SetPropertyValueCommand(
			UnitNodeProperties properties, 
			String key,
			String value){
		this.properties = properties;
		this.key = key;
		this.value = value;
		this.oldValue = properties.getProperty(key);
	}
	
	public void execute() {
		properties.setPropertyValue(key, value);
	}
	
    public String getLabel() {
        return "Set property value";
    }

    public void redo() {
        execute();
    }

    public void undo() {
		properties.setPropertyValue(key, oldValue);
    }
}