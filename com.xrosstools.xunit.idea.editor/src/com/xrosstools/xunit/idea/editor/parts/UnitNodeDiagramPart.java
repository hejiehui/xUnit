package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;
import com.xrosstools.xunit.idea.editor.figures.TopLevelUnitFigure;
import com.xrosstools.xunit.idea.editor.figures.UnitNodeDiagramFigure;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

import javax.swing.*;
import java.util.List;

public class UnitNodeDiagramPart extends EditPart {
    protected List getModelChildren() {
        return ((UnitNodeDiagram)getModel()).getUnits();
    }

    protected Figure createFigure() {
        return new UnitNodeDiagramFigure();
    }

    public Figure getContentPane(){
        return ((UnitNodeDiagramFigure)getFigure()).getContentPane();
    }

    protected void addChildVisual(EditPart childEditPart, int index) {
        Figure child = childEditPart.getFigure();
        UnitNode unit = (UnitNode)childEditPart.getModel();
        getContentPane().add(new TopLevelUnitFigure(unit.getName(), child), index);
    }

    protected void removeChildVisual(EditPart childEditPart) {
        Figure wrappedChild = childEditPart.getFigure();
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
