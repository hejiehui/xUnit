package com.xrosstools.xunit.editor.policies;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.xrosstools.xunit.editor.commands.UnitNodeContainerAddCommand;
import com.xrosstools.xunit.editor.commands.UnitNodeContainerCreateCommand;
import com.xrosstools.xunit.editor.commands.UnitNodeContainerMoveChildCommand;
import com.xrosstools.xunit.editor.model.DispatcherNode;
import com.xrosstools.xunit.editor.model.EndPointNode;
import com.xrosstools.xunit.editor.model.LocatorNode;
import com.xrosstools.xunit.editor.model.StartPointNode;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeContainer;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.editor.model.ValidatorNode;

public class UnitNodeContainerLayoutPolicy  extends FlowLayoutEditPolicy {
	/**
	 * @return <code>true</code> if the host's LayoutManager is in a horizontal
	 *         orientation
	 */
	protected boolean isHorizontal() {
		IFigure figure = getLayoutContainer();
		return ((ToolbarLayout) figure.getLayoutManager()).isHorizontal();
	}

	protected Command getCreateCommand(CreateRequest request) {
    	if(!checkAddAllowed((UnitNode)request.getNewObject()))
    		return null;
    	
    	if(!checkDropAllowed(getInsertionReference(request)))
    		return null;
    	
    	if(isTypeIncompatible(request.getNewObject()))
    		return null;
    	
        return new UnitNodeContainerCreateCommand(
        		(UnitNodeContainer)getHost().getModel(),
        		(UnitNode)request.getNewObject(), getIndex(request));
    }

	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
    	if(!checkDropAllowed(after))
    		return null;
    	
    	if(!selectable(child))
    		return null;
    	
		return new UnitNodeContainerAddCommand(
				(UnitNodeContainer)child.getParent().getModel(),
        		(UnitNodeContainer)getHost().getModel(),
        		(UnitNode)child.getModel(), getIndex(after));
	}

	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		return new UnitNodeContainerMoveChildCommand(
        		(UnitNodeContainer)getHost().getModel(),
        		(UnitNode)child.getModel(), getIndex(after));
	}
	
	private boolean checkAddAllowed(UnitNode unit){
		if(!(getHost().getModel() instanceof UnitNodeDiagram))
			return true;
		
		return !(unit instanceof StartPointNode || unit instanceof EndPointNode);
	}
	
	private boolean checkDropAllowed(EditPart after){
		return ((UnitNodeContainer)getHost().getModel()).checkDropAllowed(getIndex(after));
	}
	
	private int getIndex(Request request) {
		return getIndex(getInsertionReference(request));
	}
	
	private int getIndex(EditPart after) {
		UnitNodeContainer container = (UnitNodeContainer)getHost().getModel();
		int index = -1;
		if(after == null)
			index = container.getFixedSize() == -1 ? container.size() : container.getFixedSize();
		else
			index = container.indexOf((UnitNode)after.getModel());
		
		return index;
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
    	invalidTypes.add(DispatcherNode.class);
    	
    	return invalidTypes;
    }

}
