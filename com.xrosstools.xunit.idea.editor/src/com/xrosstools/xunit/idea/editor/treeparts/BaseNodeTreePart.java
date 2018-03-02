package com.xrosstools.xunit.idea.editor.treeparts;

import com.intellij.openapi.util.IconLoader;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BaseNodeTreePart extends TreeEditPart {
	protected List<UnitNode> getList(){
		return new ArrayList<>();
	}

    protected String getText() {
    	UnitNode node = (UnitNode)getModel();
    	if(node.getInput() == null || node.getInput().getLabel() == null)
    		return node.getName();
    
    	return node.getInput().getLabel() + SEPARATER + node.getName();
    }
    
    protected Icon getImage() {
        return IconLoader.findIcon(Activator.getIconPath(getModel().getClass()));
    }
}
