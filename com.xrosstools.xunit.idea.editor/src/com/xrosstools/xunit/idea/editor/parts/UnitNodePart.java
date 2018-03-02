package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.UnitFigure;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;

public class UnitNodePart extends BaseNodePart{
    protected Figure createFigure() {
        return new UnitFigure();
    }

    protected void refreshVisuals() {
        UnitNode unit = (UnitNode) getModel();
        UnitFigure figure = (UnitFigure)getFigure();
        figure.setLable(unit.getName());
        figure.setToolTipText(getToolTip());
        figure.setIcon(unit.getType().name());
    }
}
