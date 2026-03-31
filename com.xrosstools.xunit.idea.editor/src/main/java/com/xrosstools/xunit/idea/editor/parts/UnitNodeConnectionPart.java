package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.idea.gef.routers.MidpointLocator;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UnitNodeConnectionPart extends AbstractConnectionEditPart implements PropertyChangeListener{
    private Label label;
    public Connection createFigure() {
        Connection conn = new Connection();
        UnitNodeConnection nodeConn = (UnitNodeConnection)getModel();
        if(nodeConn.getByPassed() != null)
            conn.setRouter(new LoopUnitRouter(this));
        else
            conn.setRouter(new UnitRouter(this));

        String text = nodeConn.getDisplayText();
        label = new Label();
        label.setOpaque(true);
        conn.add(label, new UnitLocator(label));

        if(text != null){
            label.setText(text);
        }
        return conn;
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        getFigure().setLineWidth(selected ? 2 : 1);
    }

    @Override
    public void refreshVisuals() {
        UnitNodeConnection nodeConn = (UnitNodeConnection)getModel();
        if(nodeConn.getDisplayText() != null)
            label.setText(nodeConn.getDisplayText());
        else
            label.setText("");
    }
}
