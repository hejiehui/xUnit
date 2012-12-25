package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.parts.BaseNodePart;

public class OpenClassAction  extends WorkbenchPartAction implements UnitActionConstants {
	private BaseNodePart nodePart;
	public OpenClassAction(IWorkbenchPart part, BaseNodePart nodePart) {
		super(part);
		setText(OPEN_CLASS);
		this.nodePart = nodePart;
	}

	protected boolean calculateEnabled() {
		UnitNode node = (UnitNode)nodePart.getModel();
		
		return node.isValid(node.getClassName());
	}

	public void run() {
		nodePart.openClass();
	}
}
