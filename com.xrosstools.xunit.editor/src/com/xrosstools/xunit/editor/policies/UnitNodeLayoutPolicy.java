package com.xrosstools.xunit.editor.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.xrosstools.xunit.editor.commands.UnitNodeAddNodeCommand;
import com.xrosstools.xunit.editor.commands.UnitNodeCreateNodeCommand;
import com.xrosstools.xunit.editor.model.UnitNode;

public class UnitNodeLayoutPolicy extends LayoutEditPolicy {

    protected Command getAddCommand(Request request) {
    	ChangeBoundsRequest req = (ChangeBoundsRequest)request;
    	if(req.getEditParts().size() > 1)
    		return null;
    	
    	if(req.getEditParts().size() != 1)
    		return null;
    	
        EditPart child = (EditPart)req.getEditParts().get(0);
        
    	return new UnitNodeAddNodeCommand(
        		getHost().getParent().getModel(),
        		(UnitNode)getHost().getModel(),
        		child.getParent().getModel(),
        		(UnitNode)child.getModel());
    }

    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        return null;
    }

    protected Command getCreateCommand(CreateRequest request) {
        return new UnitNodeCreateNodeCommand(
        		getHost().getParent().getModel(),
        		(UnitNode)getHost().getModel(),
        		(UnitNode)request.getNewObject());
    }

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}