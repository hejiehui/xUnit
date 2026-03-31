package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

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
