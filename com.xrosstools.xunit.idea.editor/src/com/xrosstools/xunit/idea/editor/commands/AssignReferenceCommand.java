package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class AssignReferenceCommand extends Command {
	private UnitNode node;
	private String newReferenceName;
	private String oldClassName;
	private String oldReferenceName;
	private boolean executed;
	
	public AssignReferenceCommand(UnitNode node, String newReferenceName){
		this.node = node;
		this.newReferenceName = newReferenceName;
	}
	
	public void execute() {
		if(!node.isValid(newReferenceName)){
			executed = false;
			return;
		}
		
		oldClassName = node.getClassName();
		oldReferenceName = node.getReferenceName();
		node.setReferenceName(newReferenceName);
		executed = true;
	}
	
    public String getLabel() {
        return "Assign reference name";
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
