package com.xrosstools.xunit.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

import com.xrosstools.xunit.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;

public class UnitNodeConnectionPart extends AbstractConnectionEditPart implements PropertyChangeListener{
	private Label label;
    protected IFigure createFigure() {
        UnitNodeConnection nodeConn = (UnitNodeConnection)getModel();
        PolylineConnection conn = new PolylineConnection();
        conn.setTargetDecoration(new PolygonDecoration());
        conn.setForegroundColor(ColorConstants.black);
        if(nodeConn.getByPassed() != null)
        	conn.setConnectionRouter(new LoopUnitRouter(this));
        else
        	conn.setConnectionRouter(new UnitRouter(this));
        
        String text = nodeConn.getLabel();
        label = new Label();
        label.setOpaque(true);
        conn.add(label, new MidpointLocator(conn, 0));
        
        if(text != null){
        	label.setText(text);
        }

        return conn;
    }
    
    protected void createEditPolicies() {
//        installEditPolicy(EditPolicy.COMPONENT_ROLE, new DecisionTreeNodeConnectionEditPolicy());
//        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
    }

    public void setSelected(int value) {
        super.setSelected(value);
        if (value != EditPart.SELECTED_NONE)
            ((PolylineConnection) getFigure()).setLineWidth(2);
        else
            ((PolylineConnection) getFigure()).setLineWidth(1);
    }
    
    public void activate() {
    	super.activate();
    	((UnitNodeDiagram)getRoot().getContents().getModel()).getListeners().addPropertyChangeListener(this);
    	((UnitNodeConnection) getModel()).getListeners().addPropertyChangeListener(this);
    }
    
    public void deactivate() {
    	super.deactivate();
    	((UnitNodeDiagram)getRoot().getContents().getModel()).getListeners().removePropertyChangeListener(this);
    	((UnitNodeConnection) getModel()).getListeners().removePropertyChangeListener(this);
    }
    
    public void propertyChange(PropertyChangeEvent event){
    	UnitNodeConnection nodeConn = (UnitNodeConnection)getModel();
    	if(nodeConn.getLabel() != null)
    		label.setText(nodeConn.getLabel());
    	else
    		label.setText("");
    }
}
