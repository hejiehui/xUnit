package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignDefaultCommand extends Command implements UnitConstants {
	private UnitNode node;
	private String oldClassName;
	private String oldReferenceName;
	private boolean executed;
	
	public AssignDefaultCommand(UnitNode node){
		this.node = node;
	}
	
	public void execute() {
		oldClassName = node.getClassName();
		if(oldClassName != null && MSG_DEFAULT.equals(oldClassName)){
			executed = false;
			return;
		}
		
		oldReferenceName = node.getReferenceName();
		node.setClassName(MSG_DEFAULT);
		executed = true;
	}
	
    public String getLabel() {
        return "Assign to default implementation";
    }

    public void redo() {
        execute();
    }

    public void undo() {
    	if(!executed)
    		return;
    	
    	node.setClassName(oldClassName);
    	node.setReferenceName(oldReferenceName);
    }
}
