package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.util.IPropertyDescriptor;

import java.util.ArrayList;
import java.util.List;

public abstract class UnitNode extends PropertyAdapter implements XunitConstants, UnitConstants {
    private String name;
    private String description;

    private BehaviorType type = BehaviorType.processor;

    private String className = MSG_DEFAULT;
    private String moduleName;
    private String referenceName;

    private List<UnitNodeConnection> inputs = new ArrayList<>();
    private List<UnitNodeConnection> outputs = new ArrayList<>();
    private UnitNodeProperties properties = new UnitNodeProperties();

    protected UnitNodeHelper helper;

    public UnitNode(String name){
        this.name = name;
    }

    public abstract String getDefaultImplName();

    public String getImplClassName(){
        if(className == null)
            return null;

        if(!MSG_DEFAULT.equalsIgnoreCase(className))
            return getClassNamePart();

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

    public IPropertyDescriptor[] getBasicPropertyDescriptors(){
        IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
                getDescriptor(PROP_NAME),
                getDescriptor(PROP_DESCRIPTION),
                getDescriptor(PROP_CLASS),
        };

        if(isReferenceAllowed())
            descriptors = combine(descriptors, getRefencePropertyDescriptors());

        return combine(descriptors, properties.getPropertyDescriptors());
    }

    private IPropertyDescriptor[] getRefencePropertyDescriptors(){
        return new IPropertyDescriptor[]{
                getDescriptor(PROP_MODULE),
                getDescriptor(PROP_REFERENCE, getReferenceValues()),
        };
    }

    public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
        return new IPropertyDescriptor[0];
    }

    public String[] getReferenceValues(){
        return helper.getReferenceNames(this);
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] p1 = getBasicPropertyDescriptors();
        IPropertyDescriptor[] p2 = getAdditionalPropertyDescriptors();

        return combine(p1, p2);
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
        else if (PROP_DESCRIPTION.equals(propName))
            setDescription((String)value);
        else if (PROP_CLASS.equals(propName))
            setClassName((String)value);
        else if (PROP_BEHAVIOR_TYPE.equals(propName))
            setType(BehaviorType.getType((Integer)value));
        else if (PROP_REFERENCE.equals(propName)) {
            if(((Integer)value) == -1)
                setReferenceName(null);
            else
                setReferenceName(getReferenceValues()[(Integer)value]);
        }
        else if (PROP_MODULE.equals(propName))
            setModuleName((String)value);
        else
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

    public String getClassNamePart() {
        return getClassNamePart(className);
    }

    public String getMethodName() {
        return getMethodName(className);
    }

    public static String getClassNamePart(String className) {
        return className.contains(SEPARATOR) ? className.split(SEPARATOR)[0] : className;
    }

    public static String getMethodName(String className) {
        return className.contains(SEPARATOR) ? className.split(SEPARATOR)[1] : DEFAULT_METHOD;
    }


    public boolean isValid(String value){
        if(value == null)
            return false;

        return value.trim().length() > 0;
    }

    public UnitNodeHelper getHelper() {
        return helper;
    }

    public abstract boolean isReferenceAllowed();

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        if(moduleName != null)
            moduleName = moduleName.trim();

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

    public void setMethodName(String methodName) {
        if(DEFAULT_METHOD.equals(methodName) || methodName == null || methodName.trim().length() == 0)
            setClassName(getClassName().split(SEPARATOR)[0]);
        else
            setClassName(getImplClassName() + SEPARATOR + methodName);
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

    public String getInputKey(){
        UnitNodeConnection input = getInput();
        return input == null? null : input.getKey();
    }

    public void setInputKey(String key){
        UnitNodeConnection input = getInput();
        if(input != null)
            input.setKey(key);
    }

    public TaskType getTaskType(){
        UnitNodeConnection input = getInput();
        return input == null? null : input.getTaskType();
    }

    public void setTaskType(TaskType type){
        UnitNodeConnection input = getInput();
        if(input != null)
            input.setTaskType(type);
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
        firePropertyChange(PROP_OUTPUTS);
        output.getTarget().removeInput(output);
    }

    public void removeAllOutputs(){
        List<UnitNodeConnection> tempOutputs = new ArrayList<UnitNodeConnection>(outputs);
        for(UnitNodeConnection output:tempOutputs)
            removeOutput(output);
    }

    public void removeInput(UnitNodeConnection input){
        if(!inputs.contains(input))
            return;
        input.getSource().removeOutput(input);
        inputs.remove(input);
        firePropertyChange(PROP_INPUTS);
    }

    public void removeAllInputs(){
        List<UnitNodeConnection> tempInputs = new ArrayList<UnitNodeConnection>(inputs);
        for(UnitNodeConnection input:tempInputs)
            removeInput(input);
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

    public String toString() {
        return this.getClass().getName() + ": " + name;
    }
}
