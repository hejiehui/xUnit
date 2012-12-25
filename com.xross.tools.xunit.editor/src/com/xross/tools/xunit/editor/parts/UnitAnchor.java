package com.xross.tools.xunit.editor.parts;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public class UnitAnchor extends AbstractConnectionAnchor {
	public UnitAnchor(IFigure owner) {
		super(owner);
	}

	public Point getLocation(Point loc)
	{
		Rectangle r = getOwner().getBounds();
		int x = loc.x < r.x ? r.x : r.x + r.width;
		int y = r.y + r.height/2;
		Point p = new PrecisionPoint(x,y);
		getOwner().translateToAbsolute(p);
		return p;
	}
}