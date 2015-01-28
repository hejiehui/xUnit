package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RemoveEntryCommand;
import com.xross.tools.xunit.editor.model.UnitConfigure;

public class RemoveEntryAction extends WorkbenchPartAction implements UnitActionConstants {
	private String catName;
	private String key;
	private UnitConfigure config;
 
	public RemoveEntryAction(
			IWorkbenchPart part, 
			String catName,
			String key,
			UnitConfigure config){
		super(part);
		this.catName = catName;
		this.key = key;
		this.config = config;
		
		setId(ID_PREFIX + REMOVE_CATEGORY);
		setText(key);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new RemoveEntryCommand(config, catName, key));
	}
}
