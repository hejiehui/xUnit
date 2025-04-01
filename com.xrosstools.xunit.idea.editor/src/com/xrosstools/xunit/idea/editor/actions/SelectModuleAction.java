package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class SelectModuleAction extends Action implements UnitActionConstants {
	private UnitNode node;
	private String moduleName;
 
	public SelectModuleAction(
			UnitNode node,
			String moduleName){
		this.moduleName = moduleName;
		this.node = node;
		setText(moduleName);
		if(node.getModuleName() != null && node.getModuleName().equals(moduleName))
			setChecked(true);
	}
	
	@Override
	public Command createCommand() {
		return new AssignModuleCommand(node, moduleName);
	}
}
