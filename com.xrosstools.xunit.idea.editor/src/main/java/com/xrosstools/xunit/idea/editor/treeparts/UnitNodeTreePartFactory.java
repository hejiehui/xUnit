package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart parent, Object model) {
		AbstractTreeEditPart part = null;
		if(model == null)
			return part;
		
		if (model instanceof UnitNodeDiagram)
			part = new UnitNodeDiagramTreePart();
		else if (model instanceof BiBranchNode)
			part = new BiBranchNodeTreePart();
		else if (model instanceof BranchNode)
			part = new BranchNodeTreePart();
		else if (model instanceof ParallelBranchNode)
			part = new ParallelBranchNodeTreePart();
		else if (model instanceof ChainNode)
			part = new ChainNodeTreePart();
		else if (model instanceof AdapterNode)
			part = new AdapterNodeTreePart();
		else if (model instanceof DecoratorNode)
			part = new DecoratorNodeTreePart();
		else if (model instanceof LocatorNode)
			part = new LocatorNodeTreePart();
		else if (model instanceof DispatcherNode)
			part = new DispatcherNodeTreePart();
		else if (model instanceof PostValidationLoopNode)
			part = new PostValidationLoopNodeTreePart();
		else if (model instanceof PreValidationLoopNode)
			part = new PreValidationLoopNodeTreePart();
		else if (model instanceof ValidatorNode)
			part = new ValidatorNodeTreePart();
		else if (model instanceof UnitNode)
			part = new UnitNodeTreePart();

		part.setModel(model);

		return part;
	}
}