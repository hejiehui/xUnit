package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BaseNodeTreePart extends TreeEditPart {
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
