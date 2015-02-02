package com.xross.tools.xunit.editor.actions;

import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xross.tools.xunit.editor.commands.RemoveNodePropertyCommand;
import com.xross.tools.xunit.editor.model.UnitNodeProperties;

public class RemovePropertyAction extends WorkbenchPartAction implements UnitActionConstants {
	private UnitNodeProperties properties;
	private String key;
 
	public RemovePropertyAction(
			IWorkbenchPart part,
			UnitNodeProperties properties,
			String key){
		super(part);
		this.key = key;
		this.properties = properties;
		setId(ID_PREFIX + REMOVE_NODE_PROPERTY);
		setText(key);
	}
	
	protected boolean calculateEnabled() {
		return true;
	}
	
	public void run() {
		execute(new RemoveNodePropertyCommand(properties, key));
	}
}
