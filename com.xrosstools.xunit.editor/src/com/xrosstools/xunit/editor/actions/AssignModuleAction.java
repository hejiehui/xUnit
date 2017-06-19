package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.editor.model.UnitNode;

public class AssignModuleAction extends BaseDialogAction {
	private UnitNode node;
	public AssignModuleAction(IWorkbenchPart part, UnitNode node){
		super(part, ASSIGN_MODULE, ASSIGN_MODULE, node.getModuleName());
		this.node = node;
		setText(ASSIGN_TO_MODULE);
	}

	protected Command createCommand(String value) {
		return new AssignModuleCommand(node, value);
	}
}
