package com.xrosstools.xunit.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.ToolbarLayout;

import com.xrosstools.xunit.editor.model.UnitConstants;

public class TopLevelUnitFigure extends Figure implements UnitConstants{
	private Label label;
	private IFigure figure;
    public TopLevelUnitFigure(String name, IFigure figure) {
        this.figure = figure;
    	label = new Label();

    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setSpacing(TOP_LEVEL_SPACE);
    	setLayoutManager(layout);
    	
        label.setLabelAlignment(PositionConstants.LEFT);
        label.setForegroundColor(ColorConstants.blue);
        label.setText(name);

        add(label);
        add(figure);
    }

    public void setName(String name, String toolTip) {
    	label.setText(name);
    	label.setToolTip(new Label(toolTip));
        repaint();
    }
    
    public IFigure getFigure(){
    	return figure;
    }
}
