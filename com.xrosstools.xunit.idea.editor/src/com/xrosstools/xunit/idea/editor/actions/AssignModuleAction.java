package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignModuleAction extends BaseDialogAction {
	private UnitNode node;
	public AssignModuleAction(Project project, UnitNode node){
		super(project, ASSIGN_MODULE, ASSIGN_MODULE, node.getModuleName());
		this.node = node;
		setText(ASSIGN_TO_MODULE);
	}

	protected Command createCommand(String value) {
		return new AssignModuleCommand(node, value);
	}
}
