package com.xrosstools.xunit.editor.commands;

import org.eclipse.gef.commands.Command;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.editor.model.AdapterNode;
import com.xrosstools.xunit.editor.model.BiBranchNode;
import com.xrosstools.xunit.editor.model.BranchNode;
import com.xrosstools.xunit.editor.model.ChainNode;
import com.xrosstools.xunit.editor.model.CompositeUnitNode;
import com.xrosstools.xunit.editor.model.ConverterNode;
import com.xrosstools.xunit.editor.model.DispatcherNode;
import com.xrosstools.xunit.editor.model.EndPointNode;
import com.xrosstools.xunit.editor.model.IconNode;
import com.xrosstools.xunit.editor.model.LocatorNode;
import com.xrosstools.xunit.editor.model.ParallelBranchNode;
import com.xrosstools.xunit.editor.model.PostValidationLoopNode;
import com.xrosstools.xunit.editor.model.PreValidationLoopNode;
import com.xrosstools.xunit.editor.model.ProcessorNode;
import com.xrosstools.xunit.editor.model.StartPointNode;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeContainer;
import com.xrosstools.xunit.editor.model.ValidatorNode;

public class UnitNodeCreateNodeCommand extends Command {
    private CreateCommandHandler compositeHandler = new CompositeHandler();
	private CreateCommandHandler defaultHandler = new DefaultHandler();
	private boolean performed;

	private Object parent;
	private UnitNode unit;
	private UnitNode newNode;
	private UnitNode combinedNode;
	
	public UnitNodeCreateNodeCommand(Object parent, UnitNode unit, UnitNode newNode){
		this.parent = parent;
		this.unit = unit;
		this.newNode = newNode;
	}
	
	public void execute() {
		performed = false;

		if(unit instanceof CompositeUnitNode)
			compositeHandler.execute();
		
		if(!performed)
			defaultHandler.execute();	
	}
	
    public String getLabel() {
        return "Create node";
    }

    public void redo() {
        execute();
    }

    public void undo() {
		if(!performed)
			return;

		CreateCommandHandler handler = 
			combinedNode == null ? compositeHandler : defaultHandler;
		
		handler.undo();
    }

    private interface CreateCommandHandler{
    	void execute();
    	void undo();
    }

    private class CompositeHandler implements CreateCommandHandler {
    	public void execute(){
    		if(newNode instanceof IconNode)
    			return;

    		UnitNodeContainer container = ((CompositeUnitNode)unit).getContainerNode();
    		performed = container.add(newNode);
    	}
    	
    	public void undo(){
    		UnitNodeContainer container = ((CompositeUnitNode)unit).getContainerNode();
    		container.remove(newNode);
        	performed = false;
    	}
    }

    private class DefaultHandler implements CreateCommandHandler {
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
    		if(!container.contains(unit))
    			return;

    		int index = container.indexOf(unit);
    		String label = unit.getInputLabel();
    		container.remove(unit);
    		container.add(index, combineNodes());
    		combinedNode.setInputLabel(label);
    		performed = true;
    	}
    	
    	private void splitFromContainer(){
    		UnitNodeContainer container = (UnitNodeContainer)parent;
    		int index = container.indexOf(combinedNode);
    		if(index < 0)
    			return;
    		
    		String label = combinedNode.getInputLabel();
    		container.remove(combinedNode);
    		container.add(index, unit);
    		unit.setInputLabel(label);
    		performed = false;
    	}
    	
    	private UnitNode combineNodes(){
    		unit.removeAllConnections();
    		newNode.removeAllConnections();
    		if(newNode instanceof ValidatorNode)
    			return combinedNode = new BiBranchNode((ValidatorNode)newNode, unit);
    		
    		if(newNode instanceof LocatorNode)
    			return combinedNode = new BranchNode((LocatorNode)newNode, unit);
    		
            if(newNode instanceof DispatcherNode)
                return combinedNode = new ParallelBranchNode((DispatcherNode)newNode, unit);
            
    		if(newNode instanceof StartPointNode)
    			return combinedNode = new PostValidationLoopNode(unit);
    		
    		if(newNode instanceof EndPointNode)
    			return combinedNode = new PreValidationLoopNode(unit);
    		
    		if(newNode instanceof ProcessorNode && unit.getType() == BehaviorType.converter)
    			return combinedNode = new AdapterNode(BehaviorType.processor, unit);

    		if(newNode instanceof ConverterNode && unit.getType() == BehaviorType.processor)
    			return combinedNode = new AdapterNode(BehaviorType.converter, unit);
    		
    		return combinedNode = new ChainNode(unit, newNode);
    	}
    }
}
