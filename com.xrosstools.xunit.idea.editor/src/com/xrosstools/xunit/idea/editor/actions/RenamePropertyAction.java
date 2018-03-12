package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.RenamePropertyCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class RenamePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	private String oldName;
	
	public RenamePropertyAction(Project project, String oldName, UnitNodeProperties properties){
		super(project, RENAME_PROPERTY, RENAME_PROPERTY, oldName);
		this.oldName = oldName;
		this.properties = properties;
		setText(oldName);
	}

	protected Command createCommand(String value) {
		return new RenamePropertyCommand(properties, oldName, value);
	}
}
