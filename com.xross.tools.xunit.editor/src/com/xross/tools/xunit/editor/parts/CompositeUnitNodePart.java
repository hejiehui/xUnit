package com.xross.tools.xunit.editor.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

import com.xross.tools.xunit.editor.figures.CompositeUnitNodeFigure;
import com.xross.tools.xunit.editor.model.CompositeUnitNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class CompositeUnitNodePart extends BaseNodePart{
    protected List getModelChildren() {
    	List children = new ArrayList();
    	CompositeUnitNode node = (CompositeUnitNode)getModel();
    	
    	addChild(children, node.getStartNode());
    	addChild(children, node.getContainerNode());
    	addChild(children, node.getEndNode());
    	
    	return children;
    }

	protected IFigure createFigure() {
		CompositeUnitNode node = (CompositeUnitNode)getModel();
		return new CompositeUnitNodeFigure(node.isVertical(), node.getStructureType());
	}
	
    protected void refreshVisuals() {
    	super.refreshVisuals();
    	CompositeUnitNodeFigure figure = (CompositeUnitNodeFigure)getFigure();
    	figure.setLabel(((UnitNode)getModel()).getName());
    	figure.setToolTip(getToolTipLabel());
    }

	protected void addChildVisual(EditPart childEditPart, int index) {
		CompositeUnitNodeFigure figure = (CompositeUnitNodeFigure)getFigure();
		CompositeUnitNode node = (CompositeUnitNode)getModel();
		
		Object childModel = childEditPart.getModel();
		IFigure childFigure = ((GraphicalEditPart) childEditPart).getFigure();
		
		if(childModel == node.getStartNode()){
			figure.getStartPanel().add(childFigure);
			return;
		}

		if(childModel == node.getEndNode()){
			figure.getEndPanel().add(childFigure);
			return;
		}
		
		if(childModel == node.getContainerNode())
			figure.getContainerPanel().add(childFigure);
	}
}