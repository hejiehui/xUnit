package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

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
	
	abstract protected Command createCommand(String value); 
	
	public void run() {
		InputDialog dlg = new InputDialog(
				Display.getCurrent().getActiveShell(), 
				dialogTitle, dialogMessage, initialValue, null);
		
		if (dlg.open() != Window.OK)
			return;
		
		execute(createCommand(dlg.getValue()));
	}
}
