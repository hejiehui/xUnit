package com.xrosstools.xunit.editor.parts;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xrosstools.xunit.editor.model.UnitConstants;
import com.xrosstools.xunit.editor.model.UnitNodeConnection;

public class LoopUnitRouter extends AbstractRouter implements UnitConstants{
	// same x as start point
	private UnitNodeConnectionPart connPart;

	public LoopUnitRouter(UnitNodeConnectionPart connPart){
		this.connPart = connPart;
	}

    private IFigure foundByPassedFigure(){
    	UnitNodeConnection nodeConn = (UnitNodeConnection)connPart.getModel();
    	if(connPart.getSource() == null)
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
		pl.removeAllPoints();
		Point start = getStartPoint(conn);
		conn.translateToRelative(start);
		Point end = getEndPoint(conn);
		conn.translateToRelative(end);
	        
		pl.addPoint(start);
		IFigure figure = foundByPassedFigure();
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
