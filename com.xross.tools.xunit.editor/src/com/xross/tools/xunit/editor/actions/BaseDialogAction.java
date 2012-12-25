package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.UnitDiagramGraphicalEditor;
import com.xross.tools.xunit.editor.model.UnitConfigure;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;

public abstract class BaseDialogAction extends WorkbenchPartAction implements UnitActionConstants {
	private String dialogTitle;
	private String dialogMessage; 
	private String initialValue;
 
	public BaseDialogAction(
			IWorkbenchPart part, 
			String dialogTitle,
            String dialogMessage, 
            String initialValue){
		super(part);
		this.dialogTitle = dialogTitle;
		this.dialogMessage = dialogMessage;
		this.initialValue = initialValue;
		
		setId(ID_PREFIX + dialogTitle + dialogMessage + initialValue);
		setText(dialogTitle);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	abstract protected Command createCommand(UnitConfigure configure, String value); 
	
	public void run() {
		InputDialog dlg = new InputDialog(
				Display.getCurrent().getActiveShell(), 
				dialogTitle, dialogMessage, initialValue, null);
		
		if (dlg.open() != Window.OK)
			return;
		
		UnitDiagramGraphicalEditor editor = (UnitDiagramGraphicalEditor)getWorkbenchPart();
		UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();

		execute(createCommand(diagram.getConfigure(), dlg.getValue()));
	}
}
