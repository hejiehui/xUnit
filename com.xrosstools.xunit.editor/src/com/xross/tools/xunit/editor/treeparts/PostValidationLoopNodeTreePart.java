package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.PostValidationLoopNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class PostValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PostValidationLoopNode node = (PostValidationLoopNode)getModel();
		return child != node.getStartNode();
	}
}