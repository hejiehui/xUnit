package com.xrosstools.xunit.idea.editor.actions;


import com.xrosstools.xunit.idea.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class SelectModuleAction extends WorkbenchPartAction implements UnitActionConstants {
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
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new AssignModuleCommand(node, moduleName));
	}
}
