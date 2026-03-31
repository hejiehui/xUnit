package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

public class BiBranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BiBranchNode node = (BiBranchNode)getModel();
		return child != node.getEndNode();	
	}
}