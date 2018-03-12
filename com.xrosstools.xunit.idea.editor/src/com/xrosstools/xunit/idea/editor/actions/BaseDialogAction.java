package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.commands.Command;

public abstract class BaseDialogAction extends WorkbenchPartAction implements UnitActionConstants {
	private Project project;
	private String dialogTitle;
	private String dialogMessage; 
	private String initialValue;
 
	public BaseDialogAction(
			Project project,
			String dialogTitle,
            String dialogMessage, 
            String initialValue){
		this.project = project;
		this.dialogTitle = dialogTitle;
		this.dialogMessage = dialogMessage;
		this.initialValue = initialValue;
		
		setText(dialogTitle);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	abstract protected Command createCommand(String value);
	
	public void run() {
		Messages.InputDialog dialog = new Messages.InputDialog(project, dialogTitle, dialogMessage, IconLoader.findIcon(Activator.getIconPath("chain")), initialValue, new InputValidator() {
			@Override
			public boolean checkInput(String s) {
				return true;
			}

			@Override
			public boolean canClose(String s) {
				return true;
			}
		});
		dialog.show();

		if (dialog.getExitCode() == 0)
			execute(createCommand(dialog.getInputString()));
	}
}
