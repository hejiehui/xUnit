package com.xrosstools.xunit.idea.editor.actions;


import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

public class OpenClassAction extends WorkbenchPartAction implements UnitActionConstants {
	private BaseNodePart nodePart;
	public OpenClassAction(BaseNodePart nodePart) {
		setText(OPEN_CLASS);
		this.nodePart = nodePart;
	}

	protected boolean calculateEnabled() {
		UnitNode node = (UnitNode)nodePart.getModel();
		
		return node.isValid(node.getClassName());
	}

	public void run() {
		openClass();
	}

	public void openClass(){
//		if(getSourceType() == null)
//			assignClass();
//
//		if(getSourceType() == null)
//			return;
//
//		try {
//			JavaUI.openInEditor(getSourceType(), true, true);
//		} catch (CoreException x) {
//			ExceptionHandler.handle(x, JavaUIMessages.OpenTypeAction_errorTitle, JavaUIMessages.OpenTypeAction_errorMessage);
//		}
	}
//

}
