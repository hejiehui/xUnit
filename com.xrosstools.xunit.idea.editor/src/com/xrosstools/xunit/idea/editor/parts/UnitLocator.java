package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.routers.ConnectionLocator;
import com.xrosstools.idea.gef.routers.PointList;

import java.awt.*;

public class UnitLocator implements ConnectionLocator {
    private Label label;
    public UnitLocator(Label label) {
        this.label = label;
    }

    @Override
    public Point getLocation(PointList pl) {
        Point p = pl.getPoints() == 3 ? pl.get(1) : pl.get(0);
        Point p2 = pl.getPoints() == 3 ? pl.get(2) : pl.get(1);

        if(p.x + 20 + label.getWidth() > p2.x)
            return new Point(p2.x - 10 - label.getWidth(), p2.y - label.getHeight() - 5);
        else
            return new Point(p.x + 10, p.y - label.getHeight() - 5);
    }
}
