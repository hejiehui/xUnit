package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.PreValidationLoopNode;
import com.xross.tools.xunit.editor.model.UnitNode;

public class PreValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PreValidationLoopNode node = (PreValidationLoopNode)getModel();
		return child != node.getEndNode();
	}
}