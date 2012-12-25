package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.BiBranchNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class BiBranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BiBranchNode node = (BiBranchNode)getModel();
		return child != node.getEndNode();	
	}
}