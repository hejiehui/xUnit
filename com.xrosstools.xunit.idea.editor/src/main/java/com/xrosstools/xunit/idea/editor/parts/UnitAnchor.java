package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.AbstractAnchor;
import com.xrosstools.idea.gef.figures.Figure;

import java.awt.*;

public class UnitAnchor extends AbstractAnchor {
    public UnitAnchor(Figure owner) {
        setOwner(owner);
    }

    public Point getLocation(Point loc) {
        Rectangle r = new Rectangle(getOwner().getBounds());

        int x = loc.x < r.x ? r.x : r.x + r.width;
        int y = r.y + r.height/2;

        return new Point(x, y);
    }
}
