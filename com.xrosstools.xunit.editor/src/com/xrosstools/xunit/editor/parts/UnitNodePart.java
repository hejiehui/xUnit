package com.xrosstools.xunit.editor.parts;

import org.eclipse.draw2d.IFigure;

import com.xrosstools.xunit.editor.figures.UnitFigure;
import com.xrosstools.xunit.editor.model.UnitNode;

public class UnitNodePart extends BaseNodePart{
	protected IFigure createFigure() {
		return new UnitFigure();
	}
	
    protected void refreshVisuals() {
    	UnitNode unit = (UnitNode) getModel();
    	UnitFigure figure = (UnitFigure)getFigure();
        figure.setName(unit.getName());
        figure.setToolTip(getToolTipLabel());
        figure.setIcon(unit.getType().name());
    }    
}
