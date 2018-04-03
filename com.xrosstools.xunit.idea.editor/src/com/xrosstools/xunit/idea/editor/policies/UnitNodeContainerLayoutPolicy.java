package com.xrosstools.xunit.idea.editor.policies;

import com.xrosstools.xunit.idea.editor.commands.*;
import com.xrosstools.xunit.idea.editor.figures.Figure;

import java.util.HashSet;
import java.util.Set;

import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.EditPart;

public class UnitNodeContainerLayoutPolicy {
	public Command getCreateCommand(Figure target, UnitNode newNode) {
		UnitNodeContainer container = (UnitNodeContainer)target.getPart().getModel();

    	if(!checkAddAllowed(container, newNode))
    		return null;
    	
    	if(!checkDropAllowed(container, target.getInsertionIndex()))
    		return null;
    	
    	if(isTypeIncompatible(newNode))
    		return null;
    	
        return new UnitNodeContainerCreateCommand(
        		container,
        		newNode, target.getInsertionIndex());
    }

	public Command createAddCommand(Figure target, EditPart child) {
		UnitNodeContainer container = (UnitNodeContainer)target.getPart().getModel();

    	if(!checkDropAllowed(container, target.getInsertionIndex()))
    		return null;
    	
    	if(!selectable(child))
    		return null;
    	
		return new UnitNodeContainerAddCommand(
				(UnitNodeContainer)child.getParent().getModel(),
				container,
        		(UnitNode)child.getModel(), target.getInsertionIndex());
	}

	public Command createMoveChildCommand(Figure target, EditPart child) {
		return new UnitNodeContainerMoveChildCommand(
				(UnitNodeContainer)target.getPart().getModel(),
        		(UnitNode)child.getModel(), target.getInsertionIndex());
	}
	
	private boolean checkAddAllowed(UnitNodeContainer container, UnitNode unit){
		if(!(container instanceof UnitNodeDiagram))
			return true;
		
		return !(unit instanceof StartPointNode || unit instanceof EndPointNode);
	}
	
	private boolean checkDropAllowed(UnitNodeContainer container, int index){
		return container.checkDropAllowed(index);
	}

	private int getIndex(UnitNodeContainer container, UnitNode after) {
        return after == null ?
                container.getFixedSize() == -1 ? container.size() : container.getFixedSize() :
                container.indexOf(after);
	}
	
	private boolean selectable(EditPart child){
		return ((UnitNodeContainer)child.getParent().getModel()).indexOf((UnitNode)child.getModel()) >= 0; 
	}
	
    
	private boolean isTypeIncompatible(Object unit){
		return invalidTypes.contains(unit.getClass());
	}

    private Set<Class> invalidTypes = getInvalidTypes();
    private Set<Class> getInvalidTypes(){
    	Set<Class> invalidTypes = new HashSet<Class>();
    	
    	invalidTypes.add(StartPointNode.class);
    	invalidTypes.add(EndPointNode.class);
    	invalidTypes.add(ValidatorNode.class);
    	invalidTypes.add(LocatorNode.class);
    	
    	return invalidTypes;
    }

}
