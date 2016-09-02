package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.AssignDefaultCommand;
import com.xross.tools.xunit.editor.model.UnitNode;

public class AssignDefaultAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNode node;
	public AssignDefaultAction(IWorkbenchPart part, UnitNode node) {
		super(part);
		setText(ASSIGN_DEFAULT_CLASS);
		this.node = node;
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {
		execute(new AssignDefaultCommand(node));
	}
}
