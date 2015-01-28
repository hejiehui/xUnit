package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RenameNodePropertyCommand;
import com.xross.tools.xunit.editor.model.UnitNodeProperties;

public class RenameNodePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	private String oldName;
	
	public RenameNodePropertyAction(IWorkbenchPart part, String oldName, UnitNodeProperties properties){
		super(part, RENAME_PROPERTY, RENAME_NODE_PROPERTY, oldName);
		this.oldName = oldName;
		this.properties = properties;
		setText(oldName);
	}

	protected Command createCommand(String value) {
		return new RenameNodePropertyCommand(properties, oldName, value);
	}
}
