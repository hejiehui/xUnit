package com.xrosstools.xunit.idea.editor.treeparts;


import com.xrosstools.xunit.idea.editor.model.*;

public class ChainNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		ChainNode node = (ChainNode)getModel();
		return !(child == node.getStartNode() || child == node.getEndNode());
	}
}
