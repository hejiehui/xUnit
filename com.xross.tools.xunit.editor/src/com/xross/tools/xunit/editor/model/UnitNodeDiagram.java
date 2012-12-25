package com.xross.tools.xunit.editor.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;


//TODO add dependency to other package
/**
 * Entry point of containing units
 */
public class UnitNodeDiagram implements UnitNodeContainer, IPropertySource {
	private String packageId;
	private String name;
	private String description;
	private UnitConfigure configure = new UnitConfigure();
	
	private List<UnitNode> units = new ArrayList<UnitNode>();
	private List<String> imports = new ArrayList<String>();
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public String getPackageId() {
		return packageId;
	}
	
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UnitNode> getUnits() {
		return units;
	}
	
	public PropertyChangeSupport getListeners(){
		return listeners;
	}
	
	public int size(){
		return units.size();
	}
	
	public int indexOf(UnitNode unit){
		return units.indexOf(unit);
	}

	public boolean contains(UnitNode unit){
		return units.contains(unit);
	}

	public UnitNode get(int index){
		return units.get(index);
	}
	
	public List<UnitNode> getAll(){
		return units;
	}

	public boolean checkDropAllowed(int index){
		return true;
	}
	
	public boolean add(int index, UnitNode unit) {
		unit.removeAllConnections();
		units.add(index, unit);
		listeners.firePropertyChange(PROP_NODE, null, null);
		return true;
	}
	
	public boolean add(UnitNode unit){
		add(size(), unit);
		return true;
	}
	
	public void remove(UnitNode unit) {
		units.remove(unit);
		listeners.firePropertyChange(PROP_NODE, null, null);
	}
	
	public void move(int newIndex, UnitNode unit){
		int index = units.indexOf(unit);
		if(index < newIndex)
			newIndex-=1;
		
		remove(unit);
		add(newIndex, unit);
		listeners.firePropertyChange(PROP_NODE, null, null);
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getImports() {
		return imports;
	}
	
	public void addImports(String importPackage) {
		imports.add(importPackage);
	}

	public boolean isVertical() {
		return true;
	}

	public void setConfigure(UnitConfigure configure) {
		this.configure = configure;
	}

	public UnitConfigure getConfigure(){
		return configure;
	}
	
	@Override
	public int getFixedSize() {
		return -1;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		TextPropertyDescriptor[] p1 = new TextPropertyDescriptor[]{
				new TextPropertyDescriptor(PROP_NAME, PROP_NAME),
				new TextPropertyDescriptor(PROP_DESCRIPTION, PROP_DESCRIPTION),
				new TextPropertyDescriptor(PACKAGE_ID, PACKAGE_ID)
		};
//		p1[0].setCategory(CATEGORY_COMMON);
//		p1[1].setCategory(CATEGORY_COMMON);
		
		IPropertyDescriptor[] p2 = configure.getPropertyDescriptors();
		
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[p1.length + p2.length];
		System.arraycopy(p1, 0, descriptors, 0, p1.length);
		System.arraycopy(p2, 0, descriptors, p1.length, p2.length);

		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if(id instanceof String){
			String propName = (String)id;
			
			if (PROP_NAME.equals(propName))
				return name;
			if (PROP_DESCRIPTION.equals(propName))
				return description;
			if (PACKAGE_ID.equals(propName))
				return packageId;
		}
		return configure.getPropertyValue(id);
	}

	@Override
	public boolean isPropertySet(Object id) {
		return configure.isPropertySet(id);
	}

	@Override
	public void resetPropertyValue(Object id) {
		configure.resetPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(id instanceof String){
			String propName = (String)id;
			String propValue = (String)value;
			
			if (PROP_NAME.equals(propName))
				name = propValue;
			else if (PROP_DESCRIPTION.equals(propName))
				description = propValue;
			else if (PACKAGE_ID.equals(propName))
				packageId = propValue;
		}
		else
			configure.setPropertyValue(id, value);
	}
}
