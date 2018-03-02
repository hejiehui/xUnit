package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeContainerFigure;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class UnitNodeContainerPart extends EditPart implements UnitConstants, PropertyChangeListener {
    protected List<UnitNode> getModelChildren() {
        return ((UnitNodeContainer)getModel()).getAll();
    }

    protected Figure createFigure() {
        UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
        return new UnitNodeContainerFigure(unitsPanel.isVertical(), unitsPanel.getFixedSize());
    }

    protected void createEditPolicies() {
//        installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutPolicy());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }

    protected void addChildVisual(EditPart childEditPart, int index) {
        UnitNodeContainerFigure figure = (UnitNodeContainerFigure)getFigure();
        UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
        if(unitsPanel.getFixedSize() == -1){
            figure.add(childEditPart.getFigure(), index);
            return;
        }

        Figure child = childEditPart.getFigure();
        int unitIndex = unitsPanel.indexOf((UnitNode)childEditPart.getModel());
        Figure slot = (Figure) figure.getComponents().get(unitIndex);
        slot.add(child);
    }

    protected void removeChildVisual(EditPart childEditPart) {
        Figure child = childEditPart.getFigure();
        child.getParent().remove(child);
    }
}