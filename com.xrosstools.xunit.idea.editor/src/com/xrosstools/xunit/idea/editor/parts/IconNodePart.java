package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.IconFigure;
import com.xrosstools.xunit.idea.editor.model.IconNode;

public class IconNodePart extends BaseNodePart{
    protected Figure createFigure() {
        IconNode model = (IconNode)getModel();
        return new IconFigure(model.getIconId());
    }

    protected void refreshVisuals() {
        IconNode node = (IconNode)getModel();
        if(node.isShowTooltip())
            getFigure().setToolTipText(getToolTip());
        else
            getFigure().setToolTipText(null);
    }
}
