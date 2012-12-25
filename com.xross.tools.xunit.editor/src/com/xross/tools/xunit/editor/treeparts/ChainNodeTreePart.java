package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.ChainNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class ChainNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		ChainNode node = (ChainNode)getModel();
		return !(child == node.getStartNode() || child == node.getEndNode());
	}
}
