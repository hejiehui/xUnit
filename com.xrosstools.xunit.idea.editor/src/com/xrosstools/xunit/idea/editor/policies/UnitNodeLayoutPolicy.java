package com.xrosstools.xunit.idea.editor.policies;


import com.xrosstools.xunit.idea.editor.commands.*;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.EditPart;

public class UnitNodeLayoutPolicy {

    private boolean isActionAllowed(EditPart target) {
        return target != null && target.getModel() instanceof UnitNode;
    }
	public Command getAddCommand(EditPart target, EditPart child) {
        if(!isActionAllowed(target))
            return null;

        return new UnitNodeAddNodeCommand(
				target.getParent().getModel(),
        		(UnitNode)target.getModel(),
        		child.getParent().getModel(),
        		(UnitNode)child.getModel());
    }

	public Command getCreateCommand(EditPart target, UnitNode newNode) {
        if(!isActionAllowed(target))
		    return null;

        return new UnitNodeCreateNodeCommand(
				target.getParent().getModel(),
                (UnitNode) target.getModel(),
				newNode);
    }
}