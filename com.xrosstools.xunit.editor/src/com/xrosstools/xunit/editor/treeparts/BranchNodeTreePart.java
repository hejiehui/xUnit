package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.BranchNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class BranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BranchNode node = (BranchNode)getModel();
		return child != node.getEndNode();	
	}
}