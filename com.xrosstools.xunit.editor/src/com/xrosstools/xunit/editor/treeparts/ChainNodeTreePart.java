package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.ChainNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class ChainNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		ChainNode node = (ChainNode)getModel();
		return !(child == node.getStartNode() || child == node.getEndNode());
	}
}
