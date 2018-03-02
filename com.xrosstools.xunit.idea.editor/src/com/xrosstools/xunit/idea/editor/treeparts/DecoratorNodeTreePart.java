package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.UnitNode;

public class DecoratorNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		return child != null;	
	}
}
