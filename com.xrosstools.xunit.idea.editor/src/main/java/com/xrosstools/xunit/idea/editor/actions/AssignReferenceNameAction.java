package com.xrosstools.xunit.idea.editor.actions;


import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.AssignReferenceCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignReferenceNameAction extends Action implements UnitActionConstants {
	private UnitNode node;
	private String referenceName;
	public AssignReferenceNameAction(UnitNode node, String referenceName) {
		setText(referenceName);
		this.node = node;
		this.referenceName = referenceName;
		if(node.getReferenceName()!=null && node.getReferenceName().equals(referenceName))
			setChecked(true);
	}

	@Override
	public Command createCommand() {
		return new AssignReferenceCommand(node, referenceName);
	}
}
