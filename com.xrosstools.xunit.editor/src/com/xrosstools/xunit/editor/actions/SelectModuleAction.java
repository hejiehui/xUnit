package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.AssignModuleCommand;
import com.xrosstools.xunit.editor.model.UnitNode;

public class SelectModuleAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNode node;
	private String moduleName;
 
	public SelectModuleAction(
			IWorkbenchPart part,
			UnitNode node,
			String moduleName){
		super(part);
		this.moduleName = moduleName;
		this.node = node;
		setId(ID_PREFIX + REMOVE_PROPERTY);
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
