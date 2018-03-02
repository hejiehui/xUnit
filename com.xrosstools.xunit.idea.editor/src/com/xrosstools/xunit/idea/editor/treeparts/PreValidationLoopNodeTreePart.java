package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

public class PreValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PreValidationLoopNode node = (PreValidationLoopNode)getModel();
		return child != node.getEndNode();
	}
}