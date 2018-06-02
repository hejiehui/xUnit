package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

import java.util.ArrayList;
import java.util.List;

public class UnitNodeDiagramTreePart extends TreeEditPart {
    protected List<UnitNode> getModelChildren() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
    	List<UnitNode> list = new ArrayList<>();
    	list.addAll(diagram.getUnits());
    	return list;
    }

    public String getText() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
        return diagram.getPackageId() + SEPARATER + diagram.getName();
    }
}
