package com.xross.tools.xunit.editor.treeparts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import com.xross.tools.xunit.editor.Activator;
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;

public class UnitNodeDiagramTreePart extends AbstractTreeEditPart implements UnitConstants, PropertyChangeListener {
    protected List<UnitNode> getModelChildren() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
    	List<UnitNode> list = new ArrayList<UnitNode>();
    	list.addAll(diagram.getUnits());
    	return list;
    }

    protected String getText() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
        return diagram.getPackageId() + SEPARATER + diagram.getName();
    }

    protected Image getImage() {
    	return Activator.getDefault().getImage(getModel().getClass());
    }

    public void activate() {
		super.activate();
		((UnitNodeDiagram)getModel()).getListeners().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((UnitNodeDiagram)getModel()).getListeners().removePropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
        refresh();
	}
}
