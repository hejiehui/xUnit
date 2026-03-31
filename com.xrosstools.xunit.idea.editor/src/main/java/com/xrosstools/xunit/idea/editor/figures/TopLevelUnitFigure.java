package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.idea.gef.figures.ColorConstants;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.Label;
import com.xrosstools.idea.gef.figures.ToolbarLayout;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;


public class TopLevelUnitFigure extends Figure implements UnitConstants{
    private Label label;
    private Figure figure;

    public TopLevelUnitFigure(String name, Figure figure) {
        setLayoutManager(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, TOP_LEVEL_SPACE));

        label = new Label();
//        label.setForegroundColor(ColorConstants.blue);
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
