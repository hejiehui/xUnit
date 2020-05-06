package com.xrosstools.xunit.idea.editor.model;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.TextPropertyDescriptor;

import java.util.ArrayList;
import java.util.List;

public class UnitNodeDiagram extends PropertySource implements UnitNodeContainer {
    private Project project;
    private VirtualFile filePath;
    private String packageId;
    private String name;
    private String description;
    private UnitNodeProperties properties = new UnitNodeProperties();

    private List<UnitNode> units = new ArrayList<>();
    private List<String> imports = new ArrayList<>();

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public VirtualFile getFilePath() {
        return filePath;
    }

    public void setFilePath(VirtualFile filePath) {
        this.filePath = filePath;
    }

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
        firePropertyChange(PROP_NODE, null, null);
        return true;
    }

    public boolean add(UnitNode unit){
        add(size(), unit);
        return true;
    }

    public void remove(UnitNode unit) {
        units.remove(unit);
        firePropertyChange(PROP_NODE, null, null);
    }

    public void move(int newIndex, UnitNode unit){
        int index = units.indexOf(unit);
        if(index < newIndex)
            newIndex-=1;

        remove(unit);
        add(newIndex, unit);
        firePropertyChange(PROP_NODE, null, null);
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

    public UnitNodeProperties getProperties() {
        return properties;
    }

    public void setProperties(UnitNodeProperties properties) {
        this.properties = properties;
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
                new TextPropertyDescriptor(PROP_NAME),
                new TextPropertyDescriptor(PROP_DESCRIPTION),
                new TextPropertyDescriptor(PACKAGE_ID)
        };
//		p1[0].setCategory(CATEGORY_COMMON);
//		p1[1].setCategory(CATEGORY_COMMON);

        IPropertyDescriptor[] p2 = properties.getPropertyDescriptors();

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
        return properties.getPropertyValue(id);
    }

    @Override
    public boolean isPropertySet(Object id) {
        return properties.isPropertySet(id);
    }

    @Override
    public void resetPropertyValue(Object id) {
        properties.resetPropertyValue(id);
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (PROP_NAME.equals(id))
            setName((String)value);
        else if (PROP_DESCRIPTION.equals(id))
            setDescription((String)value);
        else if (PACKAGE_ID.equals(id))
            setPackageId((String)value);

        properties.setPropertyValue(id, value);
    }
}
