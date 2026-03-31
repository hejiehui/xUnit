package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BaseNodeTreePart extends AbstractTreeEditPart implements UnitConstants {
	public BaseNodeTreePart() {
		super(null);
	}

	protected List<UnitNode> getList(){
		return new ArrayList<>();
	}

	public String getText() {
    	UnitNode node = (UnitNode)getModel();
    	if(node.getInput() == null || node.getInput().getLabel() == null)
    		return node.getName();
    
    	return node.getInput().getLabel() + SEPARATER + node.getName();
    }

	public Icon getImage() {
        return Activator.getIcon(getModel().getClass());
    }
}
