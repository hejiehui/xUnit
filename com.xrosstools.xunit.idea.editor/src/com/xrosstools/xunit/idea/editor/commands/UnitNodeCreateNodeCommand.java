package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodeCreateNodeCommand extends Command {
    private CreateCommandHandler compositeHandler = new CompositeHandler();
	private CreateCommandHandler defaultHandler = new DefaultHandler();
	private boolean performed;

	private Object parent;
	private UnitNode unit;
	private UnitNode newNode;
	private CompositeUnitNode combinedNode;
	private UnitNodeConnection input;
	private UnitNodeConnection output;

	private UnitNodeConnection combinedInput;
	private UnitNodeConnection combinedOutput;

	public UnitNodeCreateNodeCommand(Object parent, UnitNode unit, UnitNode newNode){
		this.parent = parent;
		this.unit = unit;
		this.newNode = newNode;

		this.input = unit.getInput();
		this.output = unit.getOutput();
	}
	
	public void execute() {
		performed = false;

		if(unit instanceof CompositeUnitNode)
			compositeHandler.execute();
		
		if(!performed)
			defaultHandler.execute();	
	}
	
    public String getLabel() {
        return "Create node on node";
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

    		if(!performed)
    			return;

			if(combinedInput == null) {
				combinedInput = newNode.getInput();
				combinedOutput = newNode.getOutput();
			} else {
				UnitNodeConnection.restoreConnections(combinedInput, newNode, combinedOutput);
			}
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
    		container.remove(unit);
    		container.add(index, combineNodes());

			UnitNodeConnection.restoreConnections(input, combinedNode, output);

    		performed = true;
    	}
    	
    	private void splitFromContainer(){
    		UnitNodeContainer container = (UnitNodeContainer)parent;
    		int index = container.indexOf(combinedNode);
    		if(index < 0)
    			return;

    		container.remove(combinedNode);
			combinedNode.getContainerNode().remove(unit);
    		container.add(index, unit);

			UnitNodeConnection.restoreConnections(input, unit, output);

			performed = false;
    	}

    	// rename to getCombinedNode?
    	private CompositeUnitNode combineNodes(){
    		if(combinedNode == null)
				combinedNode = createCombinedNode();

    		if(combinedNode instanceof BiBranchNode)
				((BiBranchNode)combinedNode).setValidUnit(unit);
    		else if(combinedNode instanceof BranchNode)
				((BranchNode)combinedNode).addUnit("key 1", null, unit);
			else if(combinedNode instanceof ParallelBranchNode)
				((ParallelBranchNode)combinedNode).addUnit("Task 1", null, unit, TaskType.normal);
			else if(combinedNode instanceof BaseLoopNode)
				((BaseLoopNode)combinedNode).setUnit(unit);
			else if(combinedNode instanceof AdapterNode && unit.getType() == BehaviorType.converter)
				((AdapterNode)combinedNode).setUnit(unit);
    		else {
    			//ChainNode
				combinedNode.getContainerNode().add(0, unit);
			}

    		if(combinedInput == null) {
    			combinedInput = unit.getInput();
    			combinedOutput = unit.getOutput();
			} else {
    			UnitNodeConnection.restoreConnections(combinedInput, unit, combinedOutput);
			}

    		return combinedNode;
    	}
    }

    private CompositeUnitNode createCombinedNode() {
		if(newNode instanceof ValidatorNode)
			return new BiBranchNode((ValidatorNode)newNode);

		if(newNode instanceof LocatorNode)
			return new BranchNode((LocatorNode)newNode);

		if(newNode instanceof DispatcherNode)
			return new ParallelBranchNode((DispatcherNode)newNode);

		if(newNode instanceof StartPointNode)
			return new PostValidationLoopNode(true);

		if(newNode instanceof EndPointNode)
			return new PreValidationLoopNode(true);

		if(newNode instanceof ProcessorNode && unit.getType() == BehaviorType.converter)
			return new AdapterNode(BehaviorType.processor);

		if(newNode instanceof ConverterNode && unit.getType() == BehaviorType.processor)
			return new AdapterNode(BehaviorType.converter);

		ChainNode chain = new ChainNode(true);
		chain.addUnit(newNode);
		return chain;
	}
}
