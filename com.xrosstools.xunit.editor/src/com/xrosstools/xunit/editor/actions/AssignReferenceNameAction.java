package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.AssignReferenceCommand;
import com.xrosstools.xunit.editor.model.UnitNode;

public class AssignReferenceNameAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNode node;
	private String referenceName;
	public AssignReferenceNameAction(IWorkbenchPart part, UnitNode node, String referenceName) {
		super(part);
		setText(referenceName);
		this.node = node;
		this.referenceName = referenceName;
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {
		execute(new AssignReferenceCommand(node, referenceName));
	}
}
