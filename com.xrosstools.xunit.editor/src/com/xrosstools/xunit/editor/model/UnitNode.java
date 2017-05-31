package com.xrosstools.xunit.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.xrosstools.xunit.BehaviorType;

/**
 * TODO 1. if using reference, type should be set to referenced unit
 * @author jiehe
 *
 */
public abstract class UnitNode implements UnitConstants, IPropertySource {
	private String name;
	private String description;
	
	private BehaviorType type = BehaviorType.processor;
	
	private String className = MSG_DEFAULT;
	private String moduleName;
	private String referenceName;
	
	private List<UnitNodeConnection> inputs = new ArrayList<UnitNodeConnection>();
	private List<UnitNodeConnection> outputs = new ArrayList<UnitNodeConnection>();
	private UnitNodeProperties properties = new UnitNodeProperties();

	protected UnitNodeHelper helper;
	protected EditPart part;
	
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public UnitNode(String name){
		this.name = name;
	}
	
	public abstract String getDefaultImplName();
	
	public String getImplClassName(){
    	if(className == null || !MSG_DEFAULT.equalsIgnoreCase(className))
    		return className;

    	return getDefaultImplName();
	}
	
	public UnitNodeProperties getProperties() {
		return properties;
	}

	/**
	 * DecoratorNode class will override this method to getType from
	 * decoratee
	 */
	public BehaviorType getType() {
		return type;
	}
	
	/**
	 * DecoratorNode class will override this method to disable setType
	 */
	public void setType(BehaviorType type) {
		this.type = type;
		firePropertyChange(PROP_NODE);
	}
	
	protected final IPropertyDescriptor getDescriptor(String propName){
		PropertyDescriptor descriptor = new TextPropertyDescriptor(propName, propName);
		descriptor.setCategory(getCategory(propName));
		return descriptor;
	}
	
	protected final IPropertyDescriptor getDescriptor(String propName, String[] values){
		PropertyDescriptor descriptor = new ComboBoxPropertyDescriptor(propName, propName, values);
		descriptor.setCategory(getCategory(propName));
		return descriptor;
	}
	
	public IPropertyDescriptor[] getBasicPropertyDescriptors(){
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
				getDescriptor(PROP_NAME),
				getDescriptor(PROP_DESCRIPTION),
				getDescriptor(PROP_CLASS),
				getDescriptor(PROP_REFERENCE, getReferenceValues()),
				getDescriptor(PROP_MODULE),
		};
		
		return combine(descriptors, properties.getPropertyDescriptors());
	}
	
	public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
		return new IPropertyDescriptor[0];
	}
	
	protected abstract String getCategory(String id);
	public abstract String[] getReferenceValues();
	
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] p1 = getBasicPropertyDescriptors();
		IPropertyDescriptor[] p2 = getAdditionalPropertyDescriptors();
		
		return combine(p1, p2);
	}
	
	private IPropertyDescriptor[] combine(IPropertyDescriptor[] p1, IPropertyDescriptor[] p2) {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[p1.length + p2.length];
		System.arraycopy(p1, 0, descriptors, 0, p1.length);
		System.arraycopy(p2, 0, descriptors, p1.length, p2.length);
		return descriptors;
	}
	
	public Object getPropertyValue(Object propName) {
		if (PROP_NAME.equals(propName))
			return helper.getValue(name);
		if (PROP_DESCRIPTION.equals(propName))
			return helper.getValue(description);
		if (PROP_CLASS.equals(propName))
			return helper.getValue(className);
		if (PROP_BEHAVIOR_TYPE.equals(propName))
			return type.ordinal();
		if (PROP_REFERENCE.equals(propName))
			return helper.getIndex(getReferenceValues(), referenceName);
		if (PROP_MODULE.equals(propName))
			return helper.getValue(moduleName);

		return properties.getPropertyValue(propName);
	}

	public void setPropertyValue(Object propName, Object value){
		if (PROP_NAME.equals(propName))
			setName((String)value);
		if (PROP_DESCRIPTION.equals(propName))
			setDescription((String)value);
		if (PROP_CLASS.equals(propName))
			setClassName((String)value);
		if (PROP_BEHAVIOR_TYPE.equals(propName))
			setType(BehaviorType.getType((Integer)value));
		if (PROP_REFERENCE.equals(propName)) {
			if(((Integer)value) == -1)
				setReferenceName(null);
			else
				setReferenceName(getReferenceValues()[(Integer)value]);
		}
		if (PROP_MODULE.equals(propName))
			setModuleName((String)value);
			
		properties.setPropertyValue(propName, value);
	}
	
	public Object getEditableValue(){
		return this;
	}

	public boolean isPropertySet(Object propName){
		return true;
	}

	public void resetPropertyValue(Object propName){
	}
	
	public void setHelper(UnitNodeHelper helper){
		this.helper = helper;
	}
	
	public void setPart(EditPart part){
		this.part = part;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		firePropertyChange(PROP_NODE);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
		firePropertyChange(PROP_NODE);
	}
	
	public String getClassName() {
		return className;
	}

	public boolean isValid(String value){
		if(value == null)
			return false;
		
		return value.trim().length() > 0;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		if(moduleName == null || helper.isFileExist(moduleName))
			this.moduleName = moduleName;
		
		firePropertyChange(PROP_NODE);
	}

	public void setClassName(String className) {
		// To make sure there is no ambiguous value
		if(isValid(className)){
			referenceName = null;
			className = className.trim();
			if(getDefaultImplName().equalsIgnoreCase(className))
				className = MSG_DEFAULT;
			this.className = className;
		}
		firePropertyChange(PROP_NODE);
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
		// To make sure there is no ambiguous value
		if(isValid(referenceName))
			className = null;
		firePropertyChange(PROP_NODE);
	}

	public List<UnitNodeConnection> getInputs() {
		return inputs;
	}
	
	public UnitNodeConnection getInput() {
		return inputs.size() > 0 ? inputs.get(0) : null;
	}
	
	public String getInputLabel(){
		UnitNodeConnection input = getInput();
		return input == null? null : input.getLabel();
	}
	
	public void setInputLabel(String label){
		UnitNodeConnection input = getInput();
		if(input != null)
			input.setLabel(label);
	}

	public List<UnitNodeConnection> getOutputs() {
		return outputs;
	}
	
	public UnitNodeConnection getOutput() {
		return outputs.size() > 0 ? outputs.get(0) : null;
	}
	
	public void removeOutput(UnitNodeConnection output){
		if(!outputs.contains(output))
			return;
		outputs.remove(output);
		output.getTarget().removeInput(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void removeAllOutputs(){
		List<UnitNodeConnection> tempOutputs = new ArrayList<UnitNodeConnection>(outputs);
		for(UnitNodeConnection output:tempOutputs)
			removeOutput(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void removeInput(UnitNodeConnection input){
		if(!inputs.contains(input))
			return;
		inputs.remove(input);
		input.getSource().removeOutput(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	public void removeAllInputs(){
		List<UnitNodeConnection> tempInputs = new ArrayList<UnitNodeConnection>(inputs);
		for(UnitNodeConnection input:tempInputs)
			removeInput(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	public void removeAllConnections(){
		removeAllInputs();
		removeAllOutputs();
	}
	
	public void addOutput(UnitNodeConnection output){
		outputs.add(output);
		firePropertyChange(PROP_OUTPUTS);
	}
	
	public void addInput(UnitNodeConnection input){
		inputs.add(input);
		firePropertyChange(PROP_INPUTS);
	}
	
	/**
	 * Helper method for remove links from a node
	 */
	public static void removeAllConnections(UnitNode unit){
		if(unit != null)
			unit.removeAllConnections();
	}
	
	public static void removeAllInputs(UnitNode unit){
		if(unit != null)
			unit.removeAllInputs();
	}
	
	public static void removeAllOutputs(UnitNode unit){
		if(unit != null)
			unit.removeAllOutputs();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener lilistener) {
		listeners.addPropertyChangeListener(lilistener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener lilistener) {
		listeners.removePropertyChangeListener(lilistener);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue){
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	protected void firePropertyChange(String propertyName){
		firePropertyChange(propertyName, null, null);
	}
}
