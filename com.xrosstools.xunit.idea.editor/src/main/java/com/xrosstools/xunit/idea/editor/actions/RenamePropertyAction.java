package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.BaseDialogAction;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.RenamePropertyCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNodeProperties;
import org.jetbrains.uast.values.UAbstractConstant;

public class RenamePropertyAction extends BaseDialogAction implements UnitActionConstants {
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
