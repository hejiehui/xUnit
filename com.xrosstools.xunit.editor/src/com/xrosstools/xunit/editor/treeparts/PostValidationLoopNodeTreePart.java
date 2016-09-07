package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.PostValidationLoopNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class PostValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PostValidationLoopNode node = (PostValidationLoopNode)getModel();
		return child != node.getStartNode();
	}
}