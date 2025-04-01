package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UnitNodeDiagramTreePart extends AbstractTreeEditPart implements UnitConstants {
    public UnitNodeDiagramTreePart() {
        super(null);
    }
    public List<UnitNode> getModelChildren() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
    	List<UnitNode> list = new ArrayList<>();
    	list.addAll(diagram.getUnits());
    	return list;
    }

    public String getText() {
    	UnitNodeDiagram diagram = (UnitNodeDiagram)getModel();
        return diagram.getPackageId() + SEPARATER + diagram.getName();
    }

    public Icon getImage() {
        return Activator.getIcon(getModel().getClass());
    }
}
