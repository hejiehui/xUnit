package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.parts.AbstractRouter;
import com.xrosstools.xunit.idea.editor.parts.LoopUnitRouter;
import com.xrosstools.xunit.idea.editor.parts.PointList;
import com.xrosstools.xunit.idea.editor.parts.UnitRouter;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Connection extends Figure {
    private UnitNodeConnection connection;
    private Text label = new Text();
    private PointList pl;
    private GeneralPath shape = new GeneralPath();

    public void setConnection(UnitNodeConnection connection) {
        this.connection = connection;
    }

    @Override
    public void layout() {
        AbstractRouter router = connection.getByPassed() == null ? new UnitRouter(getPart()) : new LoopUnitRouter(getPart());
        pl = router.route(connection);

        int[] px = pl.getXPoints();
        int[] py = pl.getYPoints();
        shape.moveTo(px[0], py[0]);

        int count = pl.getPoints();
        for(int i = 1; i < count; i++) {
            shape.lineTo(px[i], py[i]);
        }

        for(int i = count -1; i > 0; i--) {
            shape.lineTo(px[i], py[i]);
        }

        shape.closePath();
    }

    @Override
    public void paint(Graphics graphics) {
        Stroke s = setLineWidth(graphics, isSelected() ? 2 : 1);

        AbstractRouter router = connection.getByPassed() == null ? new UnitRouter(getPart()) : new LoopUnitRouter(getPart());
        pl = router.route(connection);

        String text = connection.getLabel();
        if(text != null) {
            label.setText(text);
            label.setSize(label.getPreferredSize());
            label.setLocation(router.locate(pl, label));
            label.paint(graphics);
        }

        graphics.drawPolyline(pl.getXPoints(), pl.getYPoints(), pl.getPoints());
        restore(graphics, s);
    }

    @Override
    public boolean containsPoint(int x, int y) {
        return Path2D.contains(shape.getPathIterator(null), new Point2D.Float(x, y));
    }
}