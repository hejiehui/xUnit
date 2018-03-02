package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.Text;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.awt.*;

public class LoopUnitRouter extends AbstractRouter implements UnitConstants {
    public LoopUnitRouter(EditPart connPart) {
        super(connPart);
    }

    private Figure foundByPassedFigure(){
        UnitNodeConnection model = getModel();
        if(model.getSource() == null || model.getByPassed() == null)
            return null;

        return getPart().findFigure(model.getByPassed());
    }

    public PointList route(UnitNodeConnection conn) {
        Figure figure = foundByPassedFigure();
        int height = (figure.getSize().height + V_NODE_SPACE)/2;
        if(!conn.isFirstHalf())
            height = - height;

        Point start = height > 0 ? getSourceFigure().getBottom() : getSourceFigure().getTop();
        Point end = height > 0 ? getTargetFigure().getBottom() : getTargetFigure().getTop();

        PointList pl = new PointList();

        pl.addPoint(start);
        pl.addPoint(new Point(start.x, start.y + height));
        pl.addPoint(new Point(end.x, start.y + height));
        pl.addPoint(end);

        return pl;
    }

    @Override
    public Point locate(PointList pl, Text label) {
        int delta = pl.get(0).x > pl.get(3).x ? -label.getWidth() -5 : 5;
        return new Point(pl.get(0).x + delta, (pl.get(0).y + pl.get(1).y)/2 - label.getHeight());
    }
}
