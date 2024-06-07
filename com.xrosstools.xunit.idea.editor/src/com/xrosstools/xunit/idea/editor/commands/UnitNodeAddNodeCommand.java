package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodeAddNodeCommand extends Command {
    private CompositeHandler compositeHandler = new CompositeHandler();
	private DefaultHandler defaultHandler = new DefaultHandler();
	
	private Object parent;
	private UnitNode unit;
	private Object oldParent;
	private UnitNode newNode;
	private boolean performed;
	private ChainNode combinedNode;
	private int index;
	private UnitNodeConnection input;
	private UnitNodeConnection output;

	private UnitNodeConnection newNodeInput;
	private UnitNodeConnection newNodeOutput;

	private UnitNodeConnection addedInput;
	private UnitNodeConnection addedOutput;


	public UnitNodeAddNodeCommand(Object parent, UnitNode unit, Object oldParent, UnitNode newNode){
		this.parent = parent;
		this.unit = unit;
		this.oldParent = oldParent;
		this.newNode = newNode;

		this.input = unit.getInput();
		this.output = unit.getOutput();

		this.newNodeInput = newNode.getInput();
		this.newNodeOutput = newNode.getOutput();
	}
	
	public void execute() {
		performed = false;
		// This prevent dragging start, end, validator or locator
		if(!(oldParent instanceof UnitNodeContainer))
			return;
		
		if(unit instanceof CompositeUnitNode)
			compositeHandler.execute();
		
		if(!performed)
			defaultHandler.execute();	
	}
	
    public String getLabel() {
        return "Add node on node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
		if(!performed)
			return;

		UnitNodeAddCommandHandler handler = 
			combinedNode == null ? compositeHandler : defaultHandler;
		
		handler.undo();
	}
    
    private interface UnitNodeAddCommandHandler{
    	void execute();
    	void undo();
    }
    
    private class CompositeHandler implements UnitNodeAddCommandHandler {
    	public void execute(){
    		UnitNodeContainer container = ((CompositeUnitNode)unit).getContainerNode();
    		UnitNodeContainer oldContainer = (UnitNodeContainer)oldParent;

    		if(container == oldContainer)
    			return;
    		
    		index = oldContainer.indexOf(newNode);
    		oldContainer.remove(newNode);

    		performed = container.add(newNode);
    			
    		if(performed) {
    			if(addedInput == null) {
					addedInput = newNode.getInput();
					addedOutput = newNode.getOutput();
				} else {
    				UnitNodeConnection.restoreConnections(addedInput, newNode, addedOutput);
				}
				return;
			}

    		// if add failed (loop is not empty or bi-branch 
    		// has no available branch), add the node back to original
    		// container
    		oldContainer.add(index, newNode);
    	}
    	
    	public void undo(){
    		UnitNodeContainer container = ((CompositeUnitNode)unit).getContainerNode();
    		UnitNodeContainer oldContainer = (UnitNodeContainer)oldParent;

    		container.remove(newNode);
    		oldContainer.add(index, newNode);

			UnitNodeConnection.restoreConnections(newNodeInput, newNode, newNodeOutput);

        	performed = false;
    	}
    }

    private class DefaultHandler implements UnitNodeAddCommandHandler {
    	public void execute(){
    		if(parent instanceof UnitNodeContainer)
    			combineToContainer();
    	}
    	
    	public void undo(){
    		if(combinedNode == null)
    			return;
    		
    		if(parent instanceof UnitNodeContainer)
    			splitFromContainer();
    	}
    	
    	private void combineToContainer(){
    		UnitNodeContainer container = (UnitNodeContainer)parent;
    		UnitNodeContainer oldContainer = (UnitNodeContainer)oldParent;
    		
    		if(!container.contains(unit))
    			return;
    		
    		int newIndex = container.indexOf(unit);
    		index = oldContainer.indexOf(newNode);
    		
    		oldContainer.remove(newNode);

    		container.remove(unit);
    		
    		// Only allow combine to chain
			if(combinedNode == null)
    			combinedNode = new ChainNode(true);

			combinedNode.addUnit(unit);
			combinedNode.addUnit(newNode);

    		container.add(newIndex, combinedNode);
			UnitNodeConnection.restoreConnections(input, combinedNode, output);

    		performed = true;
    	}
    	
    	private void splitFromContainer(){
    		UnitNodeContainer container = (UnitNodeContainer)parent;
    		UnitNodeContainer oldContainer = (UnitNodeContainer)oldParent;
    		
    		int newIndex = container.indexOf(combinedNode);
    		if(newIndex < 0)
    			return;

			combinedNode.removeUnit(unit);
			combinedNode.removeUnit(newNode);

    		container.remove(combinedNode);
    		container.add(newIndex, unit);
    		oldContainer.add(index, newNode);
			UnitNodeConnection.restoreConnections(input, unit, output);
			UnitNodeConnection.restoreConnections(newNodeInput, newNode, newNodeOutput);

        	performed = false;
    	}
    }    
}
