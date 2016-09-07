package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.BiBranchNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class BiBranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BiBranchNode node = (BiBranchNode)getModel();
		return child != node.getEndNode();	
	}
}