package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.parts.AbstractRouter;
import com.xrosstools.xunit.idea.editor.parts.LoopUnitRouter;
import com.xrosstools.xunit.idea.editor.parts.PointList;
import com.xrosstools.xunit.idea.editor.parts.UnitRouter;

import java.awt.*;
import java.awt.geom.GeneralPath;
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

//        Point last = pl.getLast();
//        Polygon p = new Polygon();
//        p.addPoint(last.x - 5, last.y - 3);
//        p.addPoint(last.x, last.y);
//        p.addPoint(last.x - 5, last.y + 3);
//        graphics.fillPolygon(p);

//        shape.moveTo(px[0], py[0]);//通过移动到指定坐标（以双精度指定），将一个点添加到路径中。
//        shape.lineTo(px[1], py[1]);//通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中
//        shape.lineTo(px[2], py[2]);
//        shape.lineTo(px[3], py[3]);
//        shape.closePath();
    }

    @Override
    public boolean containsPoint(Point hit) {
        return Path2D.contains(shape.getPathIterator(null), new Point2D.Float(hit.x, hit.y));
    }
}