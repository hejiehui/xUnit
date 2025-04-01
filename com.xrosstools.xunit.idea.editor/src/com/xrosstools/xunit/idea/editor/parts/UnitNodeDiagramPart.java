package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.figures.TopLevelUnitFigure;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeDiagramFigure;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeContainerLayoutPolicy;

import java.util.List;

public class UnitNodeDiagramPart extends AbstractGraphicalEditPart {
    public List getModelChildren() {
        return ((UnitNodeDiagram)getModel()).getUnits();
    }

    protected Figure createFigure() {
        return new UnitNodeDiagramFigure();
    }

    @Override
    protected EditPolicy createEditPolicy() {
        return new UnitNodeContainerLayoutPolicy();
    }

    public void addChildVisual(EditPart childEditPart, int index) {
        Figure child = ((AbstractGraphicalEditPart)childEditPart).getFigure();
        UnitNode unit = (UnitNode)childEditPart.getModel();
        getContentPane().add(new TopLevelUnitFigure(unit.getName(), child), index);
    }

    protected void removeChildVisual(EditPart childEditPart) {
        Figure wrappedChild = ((AbstractGraphicalEditPart)childEditPart).getFigure();
        Figure wrapper = null;
        for(Object figure:getContentPane().getComponents()){
            TopLevelUnitFigure curWrapper = (TopLevelUnitFigure)figure;
            if(curWrapper.getFigure() != wrappedChild)
                continue;

            wrapper = curWrapper;
        }

        getContentPane().remove(wrapper);
    }
}
