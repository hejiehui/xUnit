package com.xrosstools.xunit.editor.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.xrosstools.xunit.BehaviorType;

public class UnitNodeConnection implements UnitConstants, PropertyChangeListener, IPropertySource, Serializable{
	private boolean firstHalf;
	private UnitNodePanel byPassed;
	private String label;
	private UnitNode source;
	private UnitNode target;
	private String srcPropName;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public static UnitNodeConnection linkStart(UnitNode source, UnitNode target){
		return new UnitNodeConnection(source, target, null, null, true);
	}
	
	public static UnitNodeConnection linkStart(UnitNode source, UnitNode target, String label){
		return new UnitNodeConnection(source, target, label, null, true);
	}

	public static UnitNodeConnection linkStart(UnitNode source, UnitNode target, UnitNodePanel byPassed){
		if(byPassed == null)
			return null;
		return new UnitNodeConnection(source, target, null, byPassed, true);
	}
	
	public static UnitNodeConnection linkEnd(UnitNode source, UnitNode target){
		return new UnitNodeConnection(source, target, null, null, false);
	}
	
	public static void remove(UnitNode source, UnitNode target){
		if(source == null || target == null)
			return;
		for(UnitNodeConnection conn: source.getOutputs()){
			if(conn.getTarget() == target){
				source.removeOutput(conn);
			}
		}
	}

	public static void remove(UnitNode source, UnitNode target, String label){
		if(source == null || target == null)
			return;
		for(UnitNodeConnection conn: source.getOutputs()){
			if(conn.getTarget() == target){
				if(label == conn.getLabel() || conn.getLabel() != null && conn.getLabel().equals(label)){
					source.removeOutput(conn);
				}
			}
		}
	}

	public static boolean contains(UnitNode source, UnitNode target){
		if(source == null || target == null)
			return false;
		for(UnitNodeConnection conn: source.getOutputs())
			if(conn.getTarget() == target)
				return true;
		return false;
	}
	
	public UnitNodeConnection(UnitNode source, UnitNode target, String label, UnitNodePanel byPassed, boolean firstHalf){
		if(source == null || target == null || contains(source, target))
			return;
		
		this.source =source;
		this.target = target;
		this.byPassed = byPassed;
		this.label = label;
		this.firstHalf = firstHalf;
		source.addOutput(this);
		target.addInput(this);
	}
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if(label == null && srcPropName == null)
			return new IPropertyDescriptor[0];
		IPropertyDescriptor[] descriptors;
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(PROP_LABEL, PROP_LABEL),
			};
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_LABEL.equals(propName))
			return label;

		return null;
	}
	
	public void setPropertyValue(Object propName, Object value){
		if (!PROP_LABEL.equals(propName))
			return;
		setLabel((String)value);
		
		if(propName == null || !isValidatorLink())
			return;
		
		((IPropertySource)source).setPropertyValue(srcPropName, label);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
	
	public void setLabel(String label){
		this.label = label;
		this.firePropertyChange(PROP_LABEL, null, label);
	}
	
	public String getLabel(){
		return label;
	}

	public UnitNode getSource() {
		return source;
	}
	public void setSource(UnitNode source) {
		this.source = source;
	}
	public UnitNode getTarget() {
		return target;
	}
	public void setTarget(UnitNode target) {
		this.target = target;
	}
	public boolean isFirstHalf() {
		return firstHalf;
	}
	public void setFirstHalf(boolean firstHalf) {
		this.firstHalf = firstHalf;
	}
	public UnitNodePanel getByPassed(){
		return byPassed;
	}
	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	public void setListeners(PropertyChangeSupport listeners) {
		this.listeners = listeners;
	}
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue){
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}

	private boolean isValidatorLink(){
		return source != null && source.getType() == BehaviorType.validator;
	}
	
	public void setPropName(String propName){
		this.srcPropName = propName;
		if(source != null)
			source.addPropertyChangeListener(this);
		propertyChange(null);
	}
	
	public void propertyChange(PropertyChangeEvent event) {
		if(!isValidatorLink())
			return;
		
		setLabel((String)((IPropertySource)source).getPropertyValue(srcPropName));
	}
}
