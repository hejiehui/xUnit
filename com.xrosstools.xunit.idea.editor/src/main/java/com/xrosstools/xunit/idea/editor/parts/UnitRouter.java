package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.routers.ConnectionRouter;
import com.xrosstools.idea.gef.routers.PointList;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.awt.*;

public class UnitRouter implements ConnectionRouter, UnitConstants {
    private UnitNodeConnectionPart connPart;
    public UnitRouter(UnitNodeConnectionPart connPart) {
        this.connPart = connPart;
    }

    public void route(Connection conn) {
        PointList pl = conn.getPoints();
        Point start = pl.getFirst();
        Point end = pl.getLast();
        pl.removeAllPoints();

        pl.addPoint(start);
        findMiddle(pl, start, end);
        pl.addPoint(end);
    }

    private void findMiddle(PointList pl, Point start, Point end){
        boolean firstHalf = ((UnitNodeConnection)connPart.getModel()).isFirstHalf();
        if(start.x == end.x || start.y == end.y)
            return;

        int x, y;
        x = firstHalf ? start.x : end.x;
        y = firstHalf ? end.y : start.y;
        pl.addPoint(new Point(x, y));
    }
}
