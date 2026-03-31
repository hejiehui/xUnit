package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

import java.util.List;

public abstract class BaseCompositeUnitNodeTreePart extends BaseNodeTreePart {
    private UnitNodePanel getUnitsPanel(){
    	return (UnitNodePanel)((CompositeUnitNode)getModel()).getContainerNode();
    }

	public final List<UnitNode> getModelChildren() {
    	CompositeUnitNode unit = (CompositeUnitNode)getModel();
    	List<UnitNode> list = getList();
    	addChild(list, unit.getStartNode());
    	for(UnitNode node: unit.getContainerNode().getAll())
    		addChild(list, node);
    	addChild(list, unit.getEndNode());
    	return list;
    }
    
    protected final void addChild(List<UnitNode> nodes, UnitNode node){
    	if(showChildNode(node))
    		nodes.add(node);
    }
    
    protected abstract boolean showChildNode(UnitNode child);
}