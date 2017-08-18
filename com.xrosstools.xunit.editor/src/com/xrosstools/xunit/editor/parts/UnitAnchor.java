package com.xrosstools.xunit.editor.parts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class UnitAnchor extends AbstractConnectionAnchor {
	public UnitAnchor(IFigure owner) {
		super(owner);
	}

	public Point getLocation(Point loc)
	{
	    Rectangle r = new Rectangle(getOwner().getBounds());
        
        Point pos;
        getOwner().translateToAbsolute(r);
        
		int x = loc.x < r.x ? r.x : r.x + r.width;
		int y = r.y + r.height/2;
		
		return new Point(x, y);
	}
}