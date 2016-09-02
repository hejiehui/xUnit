package com.xross.tools.xunit.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.TitleBarBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

import com.xross.tools.xunit.editor.model.StructureType;
import com.xross.tools.xunit.editor.model.UnitConstants;

public class CompositeUnitNodeFigure extends Figure implements UnitConstants{
	private TitleBarBorder textBorder;
	private Label footer;
	private Figure startPanel;
	private Figure containerPanel;
	private Figure endPanel;
	
	public CompositeUnitNodeFigure(boolean vertical, StructureType type){
    	setLayoutManager(getPanelLayout(vertical));
    	
    	startPanel = addPanel(this);
    	containerPanel = addPanel(this);
    	endPanel = addPanel(this);

		initBorder(type);
	}

	private void initBorder(StructureType type){
		if(!(type == StructureType.adapter || type == StructureType.decorator))
			return;
		
		((ToolbarLayout)getLayoutManager()).setSpacing(V_NODE_SPACE/2);
		Color color = type == StructureType.adapter ? ADAPTER_TITLE_COLOR : DECORATOR_TITLE_COLOR;

		textBorder = new TitleBarBorder();
		textBorder.setTextAlignment(PositionConstants.CENTER);
		textBorder.setBackgroundColor(color);
		CompoundBorder border = new CompoundBorder(
				textBorder, 
				new LineBorder(ColorConstants.darkGreen, 1, Graphics.LINE_SOLID));

		setBorder(border);
		footer =  new Label(type.name());
		footer.setForegroundColor(color);
		footer.setBorder(new LineBorder(ColorConstants.white, 1, Graphics.LINE_SOLID));
		endPanel.add(footer);
	}
	
	private ToolbarLayout getPanelLayout(boolean vertical){
    	ToolbarLayout layout= new ToolbarLayout();
    	layout.setVertical(vertical);
    	layout.setSpacing(V_NODE_SPACE);
    	layout.setStretchMinorAxis(false);
    	layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
    	return layout;
	}

	private Figure addPanel(Figure parent){
		Figure panel = new Figure();
		panel.setLayoutManager(getPanelLayout(false));
		parent.add(panel);
    	return panel;
	}

	public Figure getStartPanel() {
		return startPanel;
	}

	public Figure getContainerPanel() {
		return containerPanel;
	}

	public Figure getEndPanel() {
		return endPanel;
	}
	
	public void setLabel(String label){
		if(textBorder == null)
			return;
		
		textBorder.setLabel(label);		
	}
}
