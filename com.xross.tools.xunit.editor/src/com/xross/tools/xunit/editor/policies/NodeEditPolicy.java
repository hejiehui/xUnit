package com.xross.tools.xunit.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.xross.tools.xunit.editor.commands.DeleteNodeCommand;
import com.xross.tools.xunit.editor.model.UnitNode;

public class NodeEditPolicy extends ComponentEditPolicy {
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNodeCommand(getHost().getParent().getModel(), (UnitNode)getHost().getModel());
	}
}
