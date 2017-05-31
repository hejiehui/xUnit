package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeHelper;

public class AssignModuleAction extends BaseDialogAction {
	private UnitNode node;
	public AssignModuleAction(IWorkbenchPart part, UnitNode node){
		super(part, ASSIGN_MODULE, ASSIGN_MODULE, node.getModuleName());
		this.node = node;
		setText(ASSIGN_MODULE);
	}

	protected Command createCommand(String value) {
		if(value == null || value.trim().length() == 0 || UnitNodeHelper.isFileExist(value))
			return new AssignModuleCommand(node, value);
		else
			return null;
	}
}
