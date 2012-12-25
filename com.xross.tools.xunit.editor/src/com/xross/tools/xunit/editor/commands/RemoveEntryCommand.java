package com.xross.tools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RemoveEntryCommand extends Command {
	private UnitConfigure configure;
	private String catName;
	private String key;
	private String value;
	
	public RemoveEntryCommand(
			UnitConfigure configure, 
			String catName,
			String key){
		this.configure = configure;
		this.catName = catName;
		this.key = key;
	}
	
	public void execute() {
		value = configure.removeEntry(catName, key);
	}
	
    public String getLabel() {
        return "Remove entry";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	configure.addEntry(catName, key, value);
    }
}
