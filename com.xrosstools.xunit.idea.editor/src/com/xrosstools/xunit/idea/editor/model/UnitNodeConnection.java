package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.xunit.idea.editor.util.IPropertyDescriptor;
import com.xrosstools.xunit.idea.editor.util.IPropertySource;
import com.xrosstools.xunit.idea.editor.util.TextPropertyDescriptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class UnitNodeConnection extends PropertySource implements PropertyChangeListener, Serializable {
    private boolean firstHalf;
    private UnitNodePanel byPassed;
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
        if(source instanceof DispatcherNode) {
            return new IPropertyDescriptor[] {
                    getDescriptor(PROP_TASK_ID),
                    getDescriptor(PROP_TASK_TYPE, TaskType.values())
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
        if (PROP_LABEL.equals(propName))
            return label;
        if (PROP_TASK_ID.equals(propName))
            return label;
        if (PROP_TASK_TYPE.equals(propName))
            return taskType.ordinal();

        return null;
    }

    public void setPropertyValue(Object propName, Object value){
        if (PROP_TASK_ID.equals(propName))
            setLabel((String)value);
        if (PROP_TASK_TYPE.equals(propName))
            setTaskType(TaskType.values()[(Integer)value]);

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
