package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.IPropertySource;
import com.xrosstools.xunit.idea.editor.util.TextPropertyDescriptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnitNodeConnection extends PropertySource implements PropertyChangeListener, Serializable {
    private boolean firstHalf;
    private UnitNodePanel byPassed;
    private String key;
    private String label;
    private UnitNode source;
    private UnitNode target;
    private String srcPropName;
    private TaskType taskType;

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

        List<UnitNodeConnection> candidates = new ArrayList<>(source.getOutputs());
        for(UnitNodeConnection conn: candidates){
            if(conn.getTarget() == target){
                source.removeOutput(conn);
            }
        }
    }

    public static void remove(UnitNode source, UnitNode target, String label){
        if(source == null || target == null)
            return;

        List<UnitNodeConnection> candidates = new ArrayList<>(source.getOutputs());
        for(UnitNodeConnection conn: candidates){
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

    public UnitNodeConnection() {}

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
        if(isDispatcherLink()) {
            return new IPropertyDescriptor[] {
                    getDescriptor(PROP_TASK_ID),
                    getDescriptor(PROP_LABEL),
                    getDescriptor(PROP_TASK_TYPE, TaskType.values())
            };
        }

        if(isLocatorLink()) {
            return new IPropertyDescriptor[] {
                    getDescriptor(PROP_KEY),
                    getDescriptor(PROP_LABEL)
            };
        }

        if(label == null && srcPropName == null)
            return new IPropertyDescriptor[0];

        IPropertyDescriptor[] descriptors;
        descriptors = new IPropertyDescriptor[] {
                new TextPropertyDescriptor(PROP_LABEL),
        };
        return descriptors;
    }

    public Object getPropertyValue(Object propName) {
        if (PROP_TASK_ID.equals(propName) || PROP_KEY.equals(propName))
            return key;
        if (PROP_LABEL.equals(propName))
            return label;
        if (PROP_TASK_TYPE.equals(propName))
            return taskType.ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (PROP_TASK_ID.equals(propName) || PROP_KEY.equals(propName))
            setKey((String)value);
        if (PROP_TASK_TYPE.equals(propName))
            setTaskType(TaskType.values()[(Integer)value]);

        if (PROP_LABEL.equals(propName)) {
            setLabel((String)value);

            if(isValidatorLink())
                ((IPropertySource)source).setPropertyValue(srcPropName, label);
        }
    }

    public Object getEditableValue(){
        return this;
    }

    public boolean isPropertySet(Object propName){
        return true;
    }

    public void resetPropertyValue(Object propName){
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        this.firePropertyChange(PROP_KEY, null, label);
    }

    public void setLabel(String label){
        this.label = label == null ? null : label.trim();
        this.firePropertyChange(PROP_LABEL, null, label);
    }

    public String getLabel(){
        return label;
    }

    public String getDisplayText(){
        if(isDispatcherLink() || isLocatorLink()) {
            return label == null || label.length() == 0 ? key : label;
        }
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

    public static void restoreConnections(UnitNodeConnection input, UnitNode unit, UnitNodeConnection output) {
        if(input == null || output== null)
            return;

        unit.removeAllConnections();
        input.restoreInput(unit);
        output.restoreOutput(unit);
    }

    public void restore() {
        if(source == null || target == null)
            return;

        link(source, target);
    }

    public void restoreInput(UnitNode unit) {
        link(source, unit);
    }

    public void restoreOutput(UnitNode unit) {
        link(unit, target);
    }

    public void link(UnitNode source, UnitNode target) {
        setSource(source);
        if(!source.getOutputs().contains(this))
            source.addOutput(this);

        setTarget(target);
        if(!target.getInputs().contains(this))
            target.addInput(this);

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
    public void setByPassed(UnitNodePanel byPassed){
        this.byPassed = byPassed;
    }
    public TaskType getTaskType() {
        return taskType;
    }
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
        this.firePropertyChange(PROP_TASK_TYPE, null, taskType);
    }

    private boolean isValidatorLink(){
        return source != null && source.getType() == BehaviorType.validator;
    }

    private boolean isLocatorLink(){
        return source != null && source.getType() == BehaviorType.locator;
    }

    private boolean isDispatcherLink(){
        return source != null && source.getType() == BehaviorType.dispatcher;
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
