package com.xrosstools.xunit.idea.editor.treeparts;


import com.xrosstools.xunit.idea.editor.model.*;

public class BranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		BranchNode node = (BranchNode)getModel();
		return child != node.getEndNode();	
	}
}