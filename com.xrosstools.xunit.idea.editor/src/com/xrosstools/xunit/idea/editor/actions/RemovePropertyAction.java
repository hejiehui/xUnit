package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.commands.RemovePropertyCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class RemovePropertyAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNodeProperties properties;
	private String key;
 
	public RemovePropertyAction(
			UnitNodeProperties properties,
			String key){
		this.key = key;
		this.properties = properties;
		setText(key);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new RemovePropertyCommand(properties, key));
	}
}
