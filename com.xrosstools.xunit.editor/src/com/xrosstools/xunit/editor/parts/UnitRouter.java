package com.xrosstools.xunit.editor.parts;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import com.xrosstools.xunit.editor.model.UnitConstants;
import com.xrosstools.xunit.editor.model.UnitNodeConnection;

public class UnitRouter extends AbstractRouter implements UnitConstants {
	// same x as start point
	private UnitNodeConnectionPart connPart;

	public UnitRouter(UnitNodeConnectionPart connPart){
		this.connPart = connPart;
	}

	public void route(Connection conn) {
		PointList pl = conn.getPoints();
		pl.removeAllPoints();
		Point start = getStartPoint(conn);
		conn.translateToRelative(start);
		Point end = getEndPoint(conn);
		conn.translateToRelative(end);
	        
		pl.addPoint(start);
		algo1(pl, start, end);
		pl.addPoint(end);
	}
	
	private void algo1(PointList pl, Point start, Point end){
		if(start.x == end.x || start.y == end.y)
			return;
		
		boolean firstHalf = ((UnitNodeConnection)connPart.getModel()).isFirstHalf();
		int x, y;
		x = firstHalf ? start.x : end.x;
		y = firstHalf ? end.y : start.y;
		pl.addPoint(new Point(x, y));
	}
	
	private void algo2(PointList pl, Point start, Point end){
		if(start.x == end.x || start.y == end.y)
			return;
		
		int x = (start.x + end.x)/2;
		pl.addPoint(new Point(x, start.y));
		pl.addPoint(new Point(x, end.y));
	}

	private void algo3(PointList pl, Point start, Point end){
		if(start.x == end.x || start.y == end.y)
			return;
		
		Point p1, p2;
		boolean firstHalf = ((UnitNodeConnection)connPart.getModel()).isFirstHalf();
		if(firstHalf){
			p1 = new Point(start.x + LINK_HANDLE, start.y);
			p2 = new Point(start.x + LINK_HANDLE, end.y);
		}else{
			p1 = new Point(end.x - LINK_HANDLE, start.y);
			p2 = new Point(end.x - LINK_HANDLE, end.y);
		}
		pl.addPoint(p1);
		pl.addPoint(p2);
	}
}
