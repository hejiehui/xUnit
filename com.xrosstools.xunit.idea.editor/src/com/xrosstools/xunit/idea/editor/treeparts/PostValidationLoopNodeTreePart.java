package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

public class PostValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PostValidationLoopNode node = (PostValidationLoopNode)getModel();
		return child != node.getStartNode();
	}
}