package com.xrosstools.xunit.idea.editor.actions;


import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.AssignDefaultCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignDefaultAction extends Action implements UnitActionConstants {
	private UnitNode node;
	public AssignDefaultAction(UnitNode node) {
		setText(ASSIGN_DEFAULT_CLASS);
		this.node = node;
	}

	public Command createCommand() {
		return new AssignDefaultCommand(node);
	}
}
