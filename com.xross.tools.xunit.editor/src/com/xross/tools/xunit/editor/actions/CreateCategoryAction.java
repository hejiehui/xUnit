package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.AddCategoryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class CreateCategoryAction extends BaseDialogAction {
	private UnitConfigure configure;
	public CreateCategoryAction(IWorkbenchPart part, UnitConfigure configure){
		super(part, CREATE_CATEGORY, CREATE_CATEGORY, "");
		this.configure = configure;
	}

	protected Command createCommand(String value) {
		return new AddCategoryCommand(configure, value);
	}
}
