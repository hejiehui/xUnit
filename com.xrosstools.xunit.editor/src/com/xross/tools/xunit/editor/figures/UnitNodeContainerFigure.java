package com.xross.tools.xunit.editor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ToolbarLayout;

import com.xross.tools.xunit.editor.model.UnitConstants;

public class UnitNodeContainerFigure extends Figure implements UnitConstants{
	public UnitNodeContainerFigure(boolean vertical, int fixedSize){
		setBorder(new MarginBorder(BORDER_WIDTH));
		setLayoutManager(getPanelLayout(vertical));
    	setOpaque(false);
    	
    	for(int i = 0; i < fixedSize; i++){
    		Figure panel = new Figure();
//    		panel.setBorder(new LineBorder(1));
    		
    		panel.setLayoutManager(getPanelLayout(false));
    		add(panel);
    	}
	}

	private ToolbarLayout getPanelLayout(boolean isVertical){
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setVertical(isVertical);
    	layout.setSpacing(V_NODE_SPACE);
    	layout.setStretchMinorAxis(false);
    	layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    	setOpaque(false);
    	return layout;
	}
}
