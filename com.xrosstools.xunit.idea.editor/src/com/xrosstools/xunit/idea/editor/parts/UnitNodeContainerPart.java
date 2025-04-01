package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeContainerFigure;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeContainer;
import com.xrosstools.xunit.idea.editor.model.UnitNodePanel;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeContainerLayoutPolicy;

import java.beans.PropertyChangeListener;
import java.util.List;

public class UnitNodeContainerPart extends AbstractGraphicalEditPart implements UnitConstants, PropertyChangeListener {
    public List<UnitNode> getModelChildren() {
        return ((UnitNodeContainer)getModel()).getAll();
    }

    //We have to do this manually because UnitNodePanel is not IPropertySource
    public void activate() {
        super.activate();
        ((UnitNodePanel)getModel()).addPropertyChangeListener(this);
    }

    public void deactivate() {
        super.deactivate();
        ((UnitNodePanel)getModel()).removePropertyChangeListener(this);
    }

    protected Figure createFigure() {
        UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
        return new UnitNodeContainerFigure(unitsPanel.isVertical(), unitsPanel.getFixedSize());
    }

    @Override
    protected void refreshVisuals() {
        UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
        int fixedSize = unitsPanel.getFixedSize();
        if(fixedSize == -1)
            return;

        UnitNodeContainerFigure figure = (UnitNodeContainerFigure)getFigure();
        for(int i = 0; i < fixedSize; i++) {
            Figure slot = figure.getChildren().get(i);
            slot.getComponents().clear();
            UnitNode node = unitsPanel.get(i);
            if (node != null)
                slot.add(getContext().findEditPart(node).getFigure());
        }
    }

    @Override
    protected EditPolicy createEditPolicy() {
        return new UnitNodeContainerLayoutPolicy();
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        UnitNodeContainerFigure figure = (UnitNodeContainerFigure)getFigure();
        UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
        if(unitsPanel.getFixedSize() == -1){
            super.addChildVisual(childEditPart, index);
            return;
        }

        Figure child = ((AbstractGraphicalEditPart)childEditPart).getFigure();
        int unitIndex = unitsPanel.indexOf((UnitNode)childEditPart.getModel());
        Figure slot = (Figure) figure.getComponents().get(unitIndex);
        slot.add(child);
    }

    protected void removeChildVisual(EditPart childEditPart) {
        Figure child = ((AbstractGraphicalEditPart)childEditPart).getFigure();
        child.getParent().remove(child);
    }
}