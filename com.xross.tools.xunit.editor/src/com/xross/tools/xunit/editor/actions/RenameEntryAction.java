package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RenameEntryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RenameEntryAction extends BaseDialogAction {
	private String catName;
	private String oldName;
	private UnitConfigure configure;
	public RenameEntryAction(IWorkbenchPart part, String catName, String oldName, UnitConfigure configure){
		super(part, RENAME_PROPERTY, RENAME_PROPERTY, oldName);
		this.catName = catName;
		this.oldName = oldName;
		setText(oldName);
		this.configure= configure;
	}

	protected Command createCommand(String value) {
		return new RenameEntryCommand(configure, catName, oldName, value);
	}
}
