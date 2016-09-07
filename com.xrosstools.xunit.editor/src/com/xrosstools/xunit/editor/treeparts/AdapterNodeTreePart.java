package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.UnitNode;

public class AdapterNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		return child != null;	
	}
}
