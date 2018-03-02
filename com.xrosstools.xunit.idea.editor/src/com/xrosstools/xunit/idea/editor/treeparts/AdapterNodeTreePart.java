package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;

public class AdapterNodeTreePart extends BaseCompositeUnitNodeTreePart {
	protected boolean showChildNode(UnitNode child) {
		return child != null;	
	}
}
