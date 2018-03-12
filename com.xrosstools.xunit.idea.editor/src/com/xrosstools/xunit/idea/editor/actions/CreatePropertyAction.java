package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.CreatePropertyCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;

public class CreatePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	public CreatePropertyAction(Project project, UnitNodeProperties properties){
		super(project, CREATE_PROPERTY, CREATE_PROPERTY, "");
		this.properties = properties;
		setText(CREATE_PROPERTY);
	}

	protected Command createCommand(String value) {
		return new CreatePropertyCommand(properties, value);
	}
}
