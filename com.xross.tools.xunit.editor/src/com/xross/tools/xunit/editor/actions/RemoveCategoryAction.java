package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.UnitDiagramGraphicalEditor;
import com.xross.tools.xunit.editor.commands.RemoveCategoryCommand;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;

public class RemoveCategoryAction extends WorkbenchPartAction implements UnitActionConstants {
	private String catName;
 
	public RemoveCategoryAction(
			IWorkbenchPart part, 
			String catName){
		super(part);
		this.catName = catName;
		
		setId(ID_PREFIX + REMOVE_CATEGORY);
		setText(catName);
		
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		UnitDiagramGraphicalEditor editor = (UnitDiagramGraphicalEditor)getWorkbenchPart();
		UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();

		execute(new RemoveCategoryCommand(diagram.getConfigure(), catName));
	}
}
