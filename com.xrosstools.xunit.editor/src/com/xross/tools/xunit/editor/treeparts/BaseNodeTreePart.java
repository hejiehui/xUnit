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

public class BaseNodeTreePart extends AbstractTreeEditPart implements UnitConstants, PropertyChangeListener {
	public void activate() {
		super.activate();
		((UnitNode)getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((UnitNode)getModel()).removePropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		refresh();
	}

	protected List<UnitNode> getList(){
		return new ArrayList<UnitNode>();
	}
	
    protected void addChild(List<UnitNode> nodes, UnitNode node){
    	if(node != null)
    		nodes.add(node);
    }
    
    protected String getText() {
    	UnitNode node = (UnitNode)getModel();
    	if(node.getInput() == null || node.getInput().getLabel() == null)
    		return node.getName();
    
    	return node.getInput().getLabel() + SEPARATER + node.getName();
    }
    
    protected Image getImage() {
    	return Activator.getDefault().getImage(getModel().getClass());
    }
}
