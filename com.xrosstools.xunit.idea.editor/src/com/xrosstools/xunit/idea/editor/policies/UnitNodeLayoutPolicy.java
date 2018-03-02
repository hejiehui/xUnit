package com.xrosstools.xunit.idea.editor.policies;


import com.xrosstools.xunit.idea.editor.commands.*;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.EditPart;

public class UnitNodeLayoutPolicy {

	public Command getAddCommand(EditPart target, EditPart child) {
//    	ChangeBoundsRequest req = (ChangeBoundsRequest)request;
//    	if(req.getEditParts().size() > 1)
//    		return null;
//
//    	if(req.getEditParts().size() != 1)
//    		return null;
//
//        EditPart child = (EditPart)req.getEditParts().get(0);
        
    	return new UnitNodeAddNodeCommand(
				target.getParent().getModel(),
        		(UnitNode)target.getModel(),
        		child.getParent().getModel(),
        		(UnitNode)child.getModel());
    }

	public Command getCreateCommand(EditPart target, UnitNode newNode) {
        return new UnitNodeCreateNodeCommand(
				target.getParent().getModel(),
                (UnitNode) target.getModel(),
				newNode);
    }
}