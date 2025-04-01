package com.xrosstools.xunit.idea.editor.policies;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.commands.UnitNodeContainerAddCommand;
import com.xrosstools.xunit.idea.editor.commands.UnitNodeContainerCreateCommand;
import com.xrosstools.xunit.idea.editor.commands.UnitNodeContainerMoveChildCommand;
import com.xrosstools.xunit.idea.editor.model.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class UnitNodeContainerLayoutPolicy extends EditPolicy {
	@Override
	public Command getCreateCommand(Object newModel, Point location) {
		UnitNode newNode = (UnitNode)newModel;
		UnitNodeContainer container = (UnitNodeContainer)getHost().getModel();
		Figure target = getHost().getFigure();

    	if(!checkAddAllowed(container, newNode))
    		return null;

    	if(!checkDropAllowed(container, target.getInsertionIndex(location)))
    		return null;

    	if(isTypeIncompatible(newNode))
    		return null;

        return new UnitNodeContainerCreateCommand(
        		container,
        		newNode, target.getInsertionIndex(location));
    }

	@Override
    public Command getAddCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
		UnitNodeContainer container = (UnitNodeContainer)getHost().getModel();
		Figure target = getHost().getContentPane();
		Point location = constraint.getLocation();

    	if(!checkDropAllowed(container, target.getInsertionIndex(location)))
    		return null;

    	if(!selectable(child))
    		return null;

		return new UnitNodeContainerAddCommand(
				(UnitNodeContainer)child.getParent().getModel(),
				container,
        		(UnitNode)child.getModel(), target.getInsertionIndex(location));
	}

	@Override
	public Command getMoveCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
		Figure target = getHost().getContentPane();
		Point location = constraint.getLocation();
		return new UnitNodeContainerMoveChildCommand(
				(UnitNodeContainer)target.getPart().getModel(),
        		(UnitNode)child.getModel(), target.getInsertionIndex(location));
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
		if(child.getParent().getModel() instanceof  UnitNodeContainer)
			return ((UnitNodeContainer)child.getParent().getModel()).indexOf((UnitNode)child.getModel()) >= 0;
		else
			return false;
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
        invalidTypes.add(DispatcherNode.class);
    	
    	return invalidTypes;
    }

}
