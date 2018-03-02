package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitConstants;

import java.awt.*;

public class TopLevelUnitFigure extends Figure implements UnitConstants{
    private Label label;
    private Figure figure;

    public TopLevelUnitFigure(String name, Figure figure) {
        setLayout(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, V_NODE_SPACE));

        label = new Label();
        label.setForeground(Color.blue);
        label.setText(name);

        add(label);
        add(figure);

        this.figure = figure;
    }

    public void setName(String name, String toolTip) {
        label.setText(name);
        label.setToolTipText(toolTip);
        repaint();
    }

    public Figure getFigure(){
        return figure;
    }
}
