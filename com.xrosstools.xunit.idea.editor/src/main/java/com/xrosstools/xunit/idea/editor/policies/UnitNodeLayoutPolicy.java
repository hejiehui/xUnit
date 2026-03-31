package com.xrosstools.xunit.idea.editor.policies;


import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.commands.*;
import com.xrosstools.xunit.idea.editor.model.*;

import java.awt.*;

public class UnitNodeLayoutPolicy extends EditPolicy {
    @Override
    public boolean isInsertable(Command cmd) {
        return false;
    }

    private boolean isActionAllowed(EditPart target) {
        return target != null && target.getModel() instanceof UnitNode;
    }

    public Command getDeleteCommand() {
        return new DeleteNodeCommand(getHost().getParent().getModel(), (UnitNode)getHost().getModel());
    }

	public Command getAddCommand(AbstractGraphicalEditPart child, Rectangle constraint) {
        EditPart target = getHost();
        if(!isActionAllowed(target) || !(child.getModel() instanceof UnitNode))
            return null;

        if(child == target)
            return null;

        return new UnitNodeAddNodeCommand(
				target.getParent().getModel(),
        		(UnitNode)target.getModel(),
        		child.getParent().getModel(),
        		(UnitNode)child.getModel());
    }

	public Command getCreateCommand(Object newModel, Point location) {
        EditPart target = getHost();
        if(!isActionAllowed(target))
		    return null;

        return new UnitNodeCreateNodeCommand(
				target.getParent().getModel(),
                (UnitNode) target.getModel(),
                (UnitNode)newModel);
    }
}