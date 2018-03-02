package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.ChainNode;
import com.xrosstools.xunit.idea.editor.model.CompositeUnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

public class UnitNodeAddNodeCommand extends Command {
    private CompositeHandler compositeHandler = new CompositeHandler();
	private DefaultHandler defaultHandler = new DefaultHandler();
	
	private Object parent;
	private UnitNode unit;
	private Object oldParent;
	private UnitNode newNode;
	private boolean performed;
	private UnitNode combinedNode;
	private int index; 
	
	public UnitNodeAddNodeCommand(Object parent, UnitNode unit, Object oldParent, UnitNode newNode){
		this.parent = parent;
		this.unit = unit;
		this.oldParent = oldParent;
		this.newNode = newNode;
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
        return "Add node";
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
    		
    		index = oldContainer.indexOf(newNode);
    		oldContainer.remove(newNode);

    		performed = container.add(newNode);
    			
    		if(performed)
    			return;
    		
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

    		String label = unit.getInputLabel();
    		container.remove(unit);
    		
    		// Only allow combine to chain
    		combinedNode = new ChainNode(unit, newNode);
    		
    		container.add(newIndex, combinedNode);
    		combinedNode.setInputLabel(label);
    		performed = true;
    	}
    	
    	private void splitFromContainer(){
    		UnitNodeContainer container = (UnitNodeContainer)parent;
    		UnitNodeContainer oldContainer = (UnitNodeContainer)oldParent;
    		
    		int newIndex = container.indexOf(combinedNode);
    		if(newIndex < 0)
    			return;
    		
    		String label = combinedNode.getInputLabel();
    		container.remove(combinedNode);
    		container.add(newIndex, unit);
    		unit.setInputLabel(label);
    		oldContainer.add(index, newNode);
        	performed = false;
    	}
    }    
}
