package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.UnitDiagramGraphicalEditor;
import com.xross.tools.xunit.editor.commands.RemoveEntryCommand;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;

public class RemoveEntryAction extends WorkbenchPartAction implements UnitActionConstants {
	private String catName;
	private String key;
 
	public RemoveEntryAction(
			IWorkbenchPart part, 
			String catName,
			String key){
		super(part);
		this.catName = catName;
		this.key = key;
		
		setId(ID_PREFIX + REMOVE_CATEGORY);
		setText(key);
		
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		UnitDiagramGraphicalEditor editor = (UnitDiagramGraphicalEditor)getWorkbenchPart();
		UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();

		execute(new RemoveEntryCommand(diagram.getConfigure(), catName, key));
	}
}
