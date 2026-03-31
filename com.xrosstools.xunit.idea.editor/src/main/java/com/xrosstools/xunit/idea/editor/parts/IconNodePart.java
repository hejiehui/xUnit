package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.IconFigure;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.IconNode;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeLayoutPolicy;

public class IconNodePart extends BaseNodePart{
    protected Figure createFigure() {
        IconNode model = (IconNode)getModel();
        return new IconFigure(Activator.getIcon(model.getIconId()));
    }

    protected void refreshVisuals() {
        IconNode node = (IconNode)getModel();
        if(node.isShowTooltip())
            getFigure().setToolTipText(getToolTip());
        else
            getFigure().setToolTipText(null);
    }

    protected EditPolicy createEditPolicy() {
        return null;
    }
}
