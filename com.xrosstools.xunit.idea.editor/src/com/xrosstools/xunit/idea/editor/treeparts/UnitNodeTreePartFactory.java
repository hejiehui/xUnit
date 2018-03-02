package com.xrosstools.xunit.idea.editor.treeparts;

import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.EditContext;

public class UnitNodeTreePartFactory {
	private EditContext editContext;

	public UnitNodeTreePartFactory(EditContext editContext) {
		this.editContext = editContext;
	}

	public TreeEditPart createEditPart(TreeEditPart parent, Object model) {
		TreeEditPart part = null;
		if(model == null)
			return part;
		
		if (model instanceof UnitNodeDiagram)
			part = new UnitNodeDiagramTreePart();
		else if (model instanceof BiBranchNode)
			part = new BiBranchNodeTreePart();
		else if (model instanceof BranchNode)
			part = new BranchNodeTreePart();
		else if (model instanceof ChainNode)
			part = new ChainNodeTreePart();
		else if (model instanceof AdapterNode)
			part = new AdapterNodeTreePart();
		else if (model instanceof DecoratorNode)
			part = new DecoratorNodeTreePart();
		else if (model instanceof LocatorNode)
			part = new LocatorNodeTreePart();
		else if (model instanceof PostValidationLoopNode)
			part = new PostValidationLoopNodeTreePart();
		else if (model instanceof PreValidationLoopNode)
			part = new PreValidationLoopNodeTreePart();
		else if (model instanceof ValidatorNode)
			part = new ValidatorNodeTreePart();
		else if (model instanceof UnitNode)
			part = new UnitNodeTreePart();

		part.setEditPartFactory(this);
		part.setModel(model);
		part.setParent(parent);
		part.setContext(editContext);
//		editContext.add(part, model);


		return part;
	}
}