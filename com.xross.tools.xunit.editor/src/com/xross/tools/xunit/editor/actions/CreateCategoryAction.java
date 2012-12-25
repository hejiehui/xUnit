package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.AddCategoryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class CreateCategoryAction extends BaseDialogAction {
	public CreateCategoryAction(IWorkbenchPart part){
		super(part, CREATE_CATEGORY, CREATE_CATEGORY, "");
	}

	protected Command createCommand(UnitConfigure configure, String value) {
		return new AddCategoryCommand(configure, value);
	}
}
