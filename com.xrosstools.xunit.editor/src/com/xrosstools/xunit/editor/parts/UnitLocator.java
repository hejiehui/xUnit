package com.xrosstools.xunit.editor.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class UnitLocator extends ConnectionLocator {
    private Label label;

	public UnitLocator(Connection c, Label label) {
		super(c);
		this.label = label;
	}

	protected Point getReferencePoint() {
		PointList pl = this.getConnection().getPoints();
        Point p = pl.size() == 3 ? pl.getPoint(1) : pl.getPoint(0);
        Point p2 = pl.size() == 3 ? pl.getPoint(2) : pl.getPoint(1);

        if(p.x + 20 + label.getBounds().width() > p2.x)
            return new Point(p2.x - 10 - label.getBounds().width(), p2.y - label.getBounds().height() - 5);
        else
            return new Point(p.x + 10, p.y - label.getBounds().height() - 5);
	}
}
