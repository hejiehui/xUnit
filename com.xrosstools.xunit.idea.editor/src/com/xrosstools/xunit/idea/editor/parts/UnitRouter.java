package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.Text;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.awt.*;

public class UnitRouter extends AbstractRouter implements UnitConstants {
    public UnitRouter(EditPart connPart) {
        super(connPart);
    }

    public PointList route(UnitNodeConnection conn) {

        PointList pl = new PointList();

        Figure sourceFigure = getSourceFigure();
        Figure targetFigure = getTargetFigure();

        Point start = sourceFigure.getRight();
        Point end = targetFigure.getLeft();

        if(conn.isFirstHalf()) {
            start = start.y == end.y ? start : start.y < end.y ? sourceFigure.getTop() : sourceFigure.getBottom();
        }else{
            end = start.y == end.y ? end : start.y > end.y ? targetFigure.getTop() : targetFigure.getBottom();
        }

        pl.addPoint(start);
        findMiddle(pl, start, end, conn.isFirstHalf());
        pl.addPoint(end);
        return pl;
    }

    @Override
    public Point locate(PointList pl, Text label) {
        Point p = pl.getPoints() == 3 ? pl.get(1) : pl.get(0);
        return new Point(p.x + 10, p.y - label.getHeight() - 5);
    }

    private void findMiddle(PointList pl, Point start, Point end, boolean firstHalf){
        if(start.x == end.x || start.y == end.y)
            return;

        int x, y;
        x = firstHalf ? start.x : end.x;
        y = firstHalf ? end.y : start.y;
        pl.addPoint(new Point(x, y));
    }
}
