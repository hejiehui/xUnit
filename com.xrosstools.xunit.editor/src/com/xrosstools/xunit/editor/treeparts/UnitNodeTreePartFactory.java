package com.xrosstools.xunit.editor.treeparts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import com.xrosstools.xunit.editor.model.AdapterNode;
import com.xrosstools.xunit.editor.model.BiBranchNode;
import com.xrosstools.xunit.editor.model.BranchNode;
import com.xrosstools.xunit.editor.model.ChainNode;
import com.xrosstools.xunit.editor.model.DecoratorNode;
import com.xrosstools.xunit.editor.model.DispatcherNode;
import com.xrosstools.xunit.editor.model.LocatorNode;
import com.xrosstools.xunit.editor.model.ParallelBranchNode;
import com.xrosstools.xunit.editor.model.PostValidationLoopNode;
import com.xrosstools.xunit.editor.model.PreValidationLoopNode;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.editor.model.ValidatorNode;

public class UnitNodeTreePartFactory implements EditPartFactory {
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
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