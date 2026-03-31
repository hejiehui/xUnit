package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.routers.ConnectionRouter;
import com.xrosstools.idea.gef.routers.PointList;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.awt.*;

public class LoopUnitRouter implements ConnectionRouter, UnitConstants {
    private UnitNodeConnectionPart connPart;

    public LoopUnitRouter(UnitNodeConnectionPart connPart){
        this.connPart = connPart;
    }

    private Figure foundByPassedFigure(){
        UnitNodeConnection nodeConn = (UnitNodeConnection)connPart.getModel();
        if(nodeConn.getSource() == null || nodeConn.getByPassed() == null)
            return null;

        for(Object obj: connPart.getSource().getParent().getChildren()){
            AbstractGraphicalEditPart part = (AbstractGraphicalEditPart)obj;
            if(part.getModel() == nodeConn.getByPassed())
                return part.getFigure();
        }
        return null;
    }

    public void route(Connection conn) {
        PointList pl = conn.getPoints();
        Point start = pl.getFirst();
        Point end = pl.getLast();
        pl.removeAllPoints();

        pl.addPoint(start);
        Figure figure = foundByPassedFigure();
        if(figure != null){
            int height = (figure.getSize().height + V_NODE_SPACE)/2;
            if(!((UnitNodeConnection)connPart.getModel()).isFirstHalf())
                height = - height;
            pl.addPoint(new Point(start.x, start.y + height));
            pl.addPoint(new Point(end.x, start.y + height));
        }

        pl.addPoint(end);
    }
}
