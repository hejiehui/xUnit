package com.xross.tools.xunit.editor.treeparts;

import com.xross.tools.xunit.editor.model.UnitNode;

public class DecoratorNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		return child != null;	
	}
}
