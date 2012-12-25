package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RenameCategoryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameCategoryAction extends BaseDialogAction {
	private String oldName;
	public RenameCategoryAction(IWorkbenchPart part, String oldName){
		super(part, RENAME_CATEGORY, RENAME_CATEGORY, oldName);
		this.oldName = oldName;
		setText(oldName);
	}

	protected Command createCommand(UnitConfigure configure, String value) {
		return new RenameCategoryCommand(configure, oldName, value);
	}
}
