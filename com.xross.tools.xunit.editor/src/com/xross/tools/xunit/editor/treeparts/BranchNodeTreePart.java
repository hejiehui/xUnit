package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.BranchNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class BranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BranchNode node = (BranchNode)getModel();
		return child != node.getEndNode();	
	}
}