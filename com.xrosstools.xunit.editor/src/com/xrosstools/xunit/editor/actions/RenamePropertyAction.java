package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.RenamePropertyCommand;
import com.xrosstools.xunit.editor.model.UnitNodeProperties;

public class RenamePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	private String oldName;
	
	public RenamePropertyAction(IWorkbenchPart part, String oldName, UnitNodeProperties properties){
		super(part, RENAME_PROPERTY, RENAME_NODE_PROPERTY, oldName);
		this.oldName = oldName;
		this.properties = properties;
		setText(oldName);
	}

	protected Command createCommand(String value) {
		return new RenamePropertyCommand(properties, oldName, value);
	}
}
