package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.CreatePropertyCommand;
import com.xrosstools.xunit.editor.model.UnitNodeProperties;

public class CreatePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	public CreatePropertyAction(IWorkbenchPart part, UnitNodeProperties properties){
		super(part, CREATE_NODE_PROPERTY, CREATE_NODE_PROPERTY, "");
		this.properties = properties;
		setText(CREATE_PROPERTY);
	}

	protected Command createCommand(String value) {
		return new CreatePropertyCommand(properties, value);
	}
}
