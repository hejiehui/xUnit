package com.xrosstools.xunit.idea.editor.commands;

import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignModuleCommand extends Command {
	private UnitNode node;
	private String oldModuleName;
	private String moduleName;
	private boolean executed;
	
	public AssignModuleCommand(
			UnitNode node,
			String moduleName){
		this.node = node;
		oldModuleName = node.getModuleName();
		this.moduleName = moduleName;
	}
	
	public void execute() {
	    node.getHelper().isFileExist(moduleName);
		node.setModuleName(moduleName);
		executed = true;
	}
	
    public String getLabel() {
        return "Assign reference module";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(executed)
    		node.setModuleName(oldModuleName);
    }
}
