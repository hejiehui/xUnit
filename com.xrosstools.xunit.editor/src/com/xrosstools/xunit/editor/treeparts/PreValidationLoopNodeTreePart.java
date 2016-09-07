package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.PreValidationLoopNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class PreValidationLoopNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		PreValidationLoopNode node = (PreValidationLoopNode)getModel();
		return child != node.getEndNode();
	}
}