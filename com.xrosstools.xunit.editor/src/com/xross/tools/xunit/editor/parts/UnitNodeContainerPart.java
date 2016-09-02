package com.xross.tools.xunit.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.xross.tools.xunit.editor.figures.UnitNodeContainerFigure;
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeContainer;
import com.xross.tools.xunit.editor.model.UnitNodePanel;
import com.xross.tools.xunit.editor.policies.UnitNodeContainerLayoutPolicy;

public class UnitNodeContainerPart extends AbstractGraphicalEditPart implements UnitConstants, PropertyChangeListener {
    protected List<UnitNode> getModelChildren() {
    	return ((UnitNodeContainer)getModel()).getAll();
    }

	protected IFigure createFigure() {
		UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
		return new UnitNodeContainerFigure(unitsPanel.isVertical(), unitsPanel.getFixedSize());
	}

	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UnitNodeContainerLayoutPolicy());
	}

    public void activate() {
		super.activate();
		((UnitNodePanel)getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((UnitNodePanel)getModel()).removePropertyChangeListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
        refresh();
	}

	protected void addChildVisual(EditPart childEditPart, int index) {
		UnitNodeContainerFigure figure = (UnitNodeContainerFigure)getFigure();
		UnitNodeContainer unitsPanel = (UnitNodeContainer)getModel();
		if(unitsPanel.getFixedSize() == -1){
			super.addChildVisual(childEditPart, index);
			return;
		}

		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		int unitIndex = unitsPanel.indexOf((UnitNode)childEditPart.getModel());
		IFigure slot = (IFigure)figure.getChildren().get(unitIndex);
		slot.add(child);
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		child.getParent().remove(child);
	}
}