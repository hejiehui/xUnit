package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.RemovePropertyCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class RemovePropertyAction extends Action implements UnitActionConstants {
	private UnitNodeProperties properties;
	private String key;
 
	public RemovePropertyAction(
			UnitNodeProperties properties,
			String key){
		this.key = key;
		this.properties = properties;
		setText(key);
	}

	@Override
	public Command createCommand() {
		return new RemovePropertyCommand(properties, key);
	}
}
