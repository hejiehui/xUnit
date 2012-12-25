package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.parts.BaseNodePart;

public class AssignClassNameAction extends WorkbenchPartAction implements UnitActionConstants {
	private BaseNodePart nodePart;
	public AssignClassNameAction(IWorkbenchPart part, BaseNodePart nodePart) {
		super(part);
		setText(ASSIGN_CLASS);
		this.nodePart = nodePart;
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {
		nodePart.assignClass();
	}
}
