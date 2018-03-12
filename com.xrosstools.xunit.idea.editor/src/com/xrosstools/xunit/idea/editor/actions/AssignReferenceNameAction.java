package com.xrosstools.xunit.idea.editor.actions;


import com.xrosstools.xunit.idea.editor.commands.AssignReferenceCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignReferenceNameAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNode node;
	private String referenceName;
	public AssignReferenceNameAction(UnitNode node, String referenceName) {
		setText(referenceName);
		this.node = node;
		this.referenceName = referenceName;
		if(node.getReferenceName()!=null && node.getReferenceName().equals(referenceName))
			setChecked(true);
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run() {
		execute(new AssignReferenceCommand(node, referenceName));
	}
}
