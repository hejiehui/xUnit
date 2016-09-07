package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.AssignDefaultCommand;
import com.xrosstools.xunit.editor.model.UnitNode;

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
