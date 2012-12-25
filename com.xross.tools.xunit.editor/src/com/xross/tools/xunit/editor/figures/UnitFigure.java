package com.xross.tools.xunit.editor.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;

import com.xross.tools.xunit.editor.Activator;
import com.xross.tools.xunit.editor.model.UnitConstants;

public class UnitFigure extends RoundedRectangle implements UnitConstants {
	private Label icon;
	private Label label;
    public UnitFigure() {
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setVertical(true);
//    	layout.setStretchMinorAxis(false);
//    	layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);
    	setLayoutManager(layout);

    	icon = new Label();
    	add(icon);
        icon.setForegroundColor(ColorConstants.gray);
    	icon.setBorder(new MarginBorder(5, BORDER_WIDTH, 0, BORDER_WIDTH));//BORDER_WIDTH, 1, 0, 1));
    	
    	label = new Label();
        add(label);
        label.setBorder(new MarginBorder(0, BORDER_WIDTH, V_BORDER_WIDTH, BORDER_WIDTH));
    }
    
    private void init(){
    	BorderLayout layout= new BorderLayout();
    	setLayoutManager(layout);
    	
    	label = new Label();
        add(label);
        label.setLabelAlignment(PositionConstants.CENTER);
        label.setForegroundColor(ColorConstants.darkGreen);
        label.setIconAlignment(PositionConstants.TOP);
        label.setBorder(new MarginBorder(0, H_BORDER_WIDTH, V_BORDER_WIDTH, H_BORDER_WIDTH));
    	layout.setConstraint(label, PositionConstants.CENTER);
    
    	icon = new Label();
    	add(icon);
    	icon.setBorder(new MarginBorder(BORDER_WIDTH, 0, 0, 0));
    	layout.setConstraint(icon, PositionConstants.TOP);
    }

    public void setName(String name) {
    	label.setText(name);
    }
    
    public void setIcon(String iconId){
    	icon.setIcon(Activator.getDefault().getImageRegistry().get(iconId));
    	icon.setText(iconId);
    }
}
