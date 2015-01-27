package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.AddNodePropertyCommand;
import com.xross.tools.xunit.editor.model.UnitNodeProperties;

public class CreateNodePropertyAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	public CreateNodePropertyAction(IWorkbenchPart part, UnitNodeProperties properties){
		super(part, CREATE_PROPERTY, CREATE_PROPERTY, "");
		this.properties = properties;
		setText(CREATE_PROPERTY);
	}

	protected Command createCommand(String value) {
		return new AddNodePropertyCommand(properties, value);
	}
}
