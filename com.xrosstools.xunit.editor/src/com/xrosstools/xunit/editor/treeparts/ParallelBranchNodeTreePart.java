package com.xrosstools.xunit.editor.treeparts;

import com.xrosstools.xunit.editor.model.ParallelBranchNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class ParallelBranchNodeTreePart extends BaseCompositeUnitNodeTreePart {
    protected boolean showChildNode(UnitNode child) {
        ParallelBranchNode node = (ParallelBranchNode)getModel();
        return child != node.getEndNode();  
    }
}
