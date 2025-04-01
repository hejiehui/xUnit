package com.xrosstools.xunit.idea.editor.commands;


import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignClassCommand extends Command implements UnitConstants {
	private UnitNode node;
	private String newName;
	private String oldClassName;
	private String oldReferenceName;
	private boolean executed;
	
	public AssignClassCommand(UnitNode node, String newName){
		this.node = node;
		this.newName = newName;
		if(newName == node.getDefaultImplName())
			newName = MSG_DEFAULT;
	}
	
	public void execute() {
		if(!node.isValid(newName)){
			executed = false;
			return;
		}

		oldClassName = node.getClassName();
		oldReferenceName = node.getReferenceName();
		node.setClassName(newName);
		executed = true;
	}
	
    public String getLabel() {
        return "Assign implementation class";
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
