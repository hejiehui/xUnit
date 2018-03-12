package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.SetPropertyValueCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class SetPropertyValueAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	private String key;
	
	public SetPropertyValueAction(Project project, UnitNodeProperties properties, String key){
		super(project, SET_PROPERTY, SET_PROPERTY + " " + key , "");
		this.properties = properties;
		this.key = key;
		setText(key);
		if(properties.contains(key))
		    setChecked(true);
	}

	protected Command createCommand(String value) {
		return new SetPropertyValueCommand(properties, key, value);
	}
}
