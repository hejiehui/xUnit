package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.CompositeUnitNodeFigure;
import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.model.CompositeUnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import java.util.ArrayList;
import java.util.List;

public class CompositeUnitNodePart extends BaseNodePart{
    protected List getModelChildren() {
        List children = new ArrayList();
        CompositeUnitNode node = (CompositeUnitNode)getModel();

        addChild(children, node.getStartNode());
        addChild(children, node.getContainerNode());
        addChild(children, node.getEndNode());

        return children;
    }

    protected Figure createFigure() {
        CompositeUnitNode node = (CompositeUnitNode)getModel();
        return new CompositeUnitNodeFigure(!node.isVertical(), node.getStructureType());
    }

    protected void refreshVisuals() {
        CompositeUnitNodeFigure figure = (CompositeUnitNodeFigure)getFigure();
        figure.setLabel(((UnitNode)getModel()).getName());
        figure.setToolTipText(getToolTip());
    }

    protected void addChildVisual(EditPart childEditPart, int index) {
        CompositeUnitNodeFigure figure = (CompositeUnitNodeFigure)getFigure();
        CompositeUnitNode node = (CompositeUnitNode)getModel();

        Object childModel = childEditPart.getModel();
        Figure childFigure = childEditPart.getFigure();

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