package com.xrosstools.xunit.editor.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.SetPropertyValueCommand;
import com.xrosstools.xunit.editor.model.UnitNodeProperties;

public class SetPropertyValueAction extends BaseDialogAction {
	private UnitNodeProperties properties;
	private String key;
	
	public SetPropertyValueAction(IWorkbenchPart part, UnitNodeProperties properties, String key){
		super(part, SET_PROPERTY, SET_PROPERTY + " " + key , "");
		this.properties = properties;
		this.key = key;
		setText(key);
		if(properties.contains(key))
		    setChecked(true);
	}

	protected Command createCommand(String value) {
		return new SetPropertyValueCommand(properties, key, value);
	}
}
