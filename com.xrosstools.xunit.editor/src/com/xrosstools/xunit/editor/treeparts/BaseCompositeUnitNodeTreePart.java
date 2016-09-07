package com.xrosstools.xunit.editor.treeparts;

import java.util.List;

import com.xrosstools.xunit.editor.model.CompositeUnitNode;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodePanel;

public abstract class BaseCompositeUnitNodeTreePart extends BaseNodeTreePart {
    private UnitNodePanel getUnitsPanel(){
    	return (UnitNodePanel)((CompositeUnitNode)getModel()).getContainerNode();
    }

    public void activate() {
		super.activate();
		getUnitsPanel().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		getUnitsPanel().removePropertyChangeListener(this);
	}

    protected final List<UnitNode> getModelChildren() {
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
    		super.addChild(nodes, node);
    }
    
    protected abstract boolean showChildNode(UnitNode child);
}