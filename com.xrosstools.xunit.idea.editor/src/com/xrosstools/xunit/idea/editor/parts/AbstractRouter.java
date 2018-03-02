package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.Text;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.awt.*;

public abstract class AbstractRouter {
    private EditPart part;
    private UnitNodeConnection model;
    private Figure sourceFigure;
    private Figure targetFigure;

    public AbstractRouter(EditPart part) {
        this.part = part;
        model = (UnitNodeConnection)part.getModel();
        sourceFigure = part.findFigure(model.getSource());
        targetFigure = part.findFigure(model.getTarget());
    }

    public EditPart getPart() {
        return part;
    }

    public UnitNodeConnection getModel() {
        return model;
    }

    public Figure getSourceFigure() {
        return sourceFigure;
    }

    public Figure getTargetFigure() {
        return targetFigure;
    }

    public abstract PointList route(UnitNodeConnection conn);

    public abstract Point locate(PointList pl, Text label);
}
