package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.parts.AbstractRouter;
import com.xrosstools.xunit.idea.editor.parts.LoopUnitRouter;
import com.xrosstools.xunit.idea.editor.parts.PointList;
import com.xrosstools.xunit.idea.editor.parts.UnitRouter;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Connection extends Figure {
    public static final int DELTA = 2;
    private UnitNodeConnection connection;
    private Text label = new Text();
    private PointList pl;
    private GeneralPath shape;

    public void setConnection(UnitNodeConnection connection) {
        this.connection = connection;
    }

    @Override
    public void layout() {
    }

    private void updatePath() {
        shape = new GeneralPath();

        int[] px = pl.getXPoints();
        int[] py = pl.getYPoints();
        shape.moveTo(px[0], py[0]);

        int count = pl.getPoints();
        for(int i = 1; i < count; i++) {
            shape.lineTo(px[i], py[i]);
        }

        for(int i = count -2; i > 0; i--) {
            shape.lineTo(px[i], py[i]);
        }

        shape.closePath();
    }

    @Override
    public void paint(Graphics graphics) {
        Stroke s = setLineWidth(graphics, isSelected() ? 2 : 1);

        AbstractRouter router = connection.getByPassed() == null ? new UnitRouter(getPart()) : new LoopUnitRouter(getPart());
        pl = router.route(connection);
        updatePath();

        String text = connection.getDisplayText();
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
//        Path2D.contains(shape.getPathIterator(null), new Point2D.Float(x, y))
//        shape.contains(new Point2D.Float(x, y))
        if(pl == null)
            return false;

        if(label.containsPoint(x, y))
            return true;

        int count = pl.getPoints();
        for(int i = 1; i < count; i++) {
            Point start = pl.get(i-1);
            Point end = pl.get(i);

            if(contains(start,end, x, y)) {
//                System.out.println("found: " + connection.getLabel());
                return true;
            }
        }
        return false;
    }

    private boolean contains(Point start, Point end, int x, int y) {
        // Vertical case
        if(start.x == end.x) {
            return between(start.y, end.y, y) && near(start.x, x);
        }else{
            return between(start.x, end.x, x) && near(start.y, y);
        }
    }

    private boolean between(int p1, int p2, int p) {
        return p1 <= p &&  p <= p2 || p2 <= p &&  p <= p1;
    }

    private boolean near(int p1, int p) {
        return Math.abs(p1 - p) <= DELTA;
    }
}