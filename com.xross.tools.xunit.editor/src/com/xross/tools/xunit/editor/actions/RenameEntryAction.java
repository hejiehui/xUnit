package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RenameEntryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameEntryAction extends BaseDialogAction {
	private String catName;
	private String oldName;
	public RenameEntryAction(IWorkbenchPart part, String catName, String oldName){
		super(part, RENAME_PROPERTY, RENAME_PROPERTY, oldName);
		this.catName = catName;
		this.oldName = oldName;
		setText(oldName);
	}

	protected Command createCommand(UnitConfigure configure, String value) {
		return new RenameEntryCommand(configure, catName, oldName, value);
	}
}
