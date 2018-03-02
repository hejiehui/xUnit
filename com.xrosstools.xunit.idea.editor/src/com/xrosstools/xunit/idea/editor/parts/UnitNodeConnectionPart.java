package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Connection;
import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class UnitNodeConnectionPart extends EditPart implements PropertyChangeListener{
    private JLabel label;
    public Connection createFigure() {
        Connection figure = new Connection();
        UnitNodeConnection model = (UnitNodeConnection)getModel();
        figure.setConnection(model);
        figure.setPart(this);
        return figure;
    }

//    public void setSelected(int value) {
//        super.setSelected(value);
//        if (value != EditPart.SELECTED_NONE)
//            ((PolylineConnection) getFigure()).setLineWidth(2);
//        else
//            ((PolylineConnection) getFigure()).setLineWidth(1);
//    }
//
//    public void propertyChange(PropertyChangeEvent event){
//        UnitNodeConnection nodeConn = (UnitNodeConnection)getModel();
//        if(nodeConn.getLabel() != null)
//            label.setText(nodeConn.getLabel());
//        else
//            label.setText("");
//    }
}
