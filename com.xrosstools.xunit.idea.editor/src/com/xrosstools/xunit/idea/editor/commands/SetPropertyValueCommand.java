package com.xrosstools.xunit.idea.editor.commands;

import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.util.IPropertySource;

import java.beans.PropertyChangeEvent;

public class SetPropertyValueCommand extends Command {
	private IPropertySource properties;
	private Object key;
	private Object oldValue;
	private Object value;
	
	public SetPropertyValueCommand(
			IPropertySource properties,
			Object key,
			Object value){
		this.properties = properties;
		this.key = key;
		this.value = value;
		this.oldValue = properties.getPropertyValue(key);
	}

	public SetPropertyValueCommand(PropertyChangeEvent evt) {
		this((IPropertySource)evt.getSource(), evt.getPropertyName(), evt.getNewValue());
	}
	
	public void execute() {
		properties.setPropertyValue(key, value);
	}
	
    public String getLabel() {
        return "Set property value";
    }

    public void redo() {
        execute();
    }

    public void undo() {
		properties.setPropertyValue(key, oldValue);
    }
}