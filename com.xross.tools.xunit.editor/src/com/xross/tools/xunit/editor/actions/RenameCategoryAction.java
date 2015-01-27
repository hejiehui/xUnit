package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RenameCategoryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameCategoryAction extends BaseDialogAction {
	private String oldName;
	private UnitConfigure configure;
	public RenameCategoryAction(IWorkbenchPart part, String oldName, UnitConfigure configure){
		super(part, RENAME_CATEGORY, RENAME_CATEGORY, oldName);
		this.oldName = oldName;
		setText(oldName);
		this.configure = configure;
	}

	protected Command createCommand(String value) {
		return new RenameCategoryCommand(configure, oldName, value);
	}
}
