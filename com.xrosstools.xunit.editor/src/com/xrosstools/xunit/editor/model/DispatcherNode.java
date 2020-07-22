package com.xrosstools.xunit.editor.model;

import java.util.concurrent.TimeUnit;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.editor.Activator;

public class DispatcherNode extends IconNode {
    private long timeout = DEFAULT_TIMEOUT;
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;
    private CompletionMode completionMode = CompletionMode.all;
    
    public DispatcherNode(){
        super(BehaviorType.dispatcher, Activator.DISPATCHER, true);
    }
    
    public void addOutput(UnitNodeConnection output){
        super.addOutput(output);
    }

    protected String getCategory(String id) {
        return id == PROP_COMPLETION_MODE || id == PROP_TIMEOUT || id == PROP_TIME_UNIT  ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public IPropertyDescriptor[] getAdditionalPropertyDescriptors(){
        IPropertyDescriptor[] descriptors = new IPropertyDescriptor[]{
                getDescriptor(PROP_COMPLETION_MODE, CompletionMode.values()),
                getDescriptor(PROP_TIMEOUT),
                getDescriptor(PROP_TIME_UNIT, TimeUnit.values()),
        };
        
        return descriptors;
    }
    
    public Object getPropertyValue(Object propName) {
        if (PROP_COMPLETION_MODE.equals(propName))
            return getCompletionMode().ordinal();
        if (PROP_TIMEOUT.equals(propName))
            return String.valueOf(getTimeout());
        if (PROP_TIME_UNIT.equals(propName))
            return getTimeUnit().ordinal();

        return super.getPropertyValue(propName);
    }

    public void setPropertyValue(Object propName, Object value){
        if (PROP_COMPLETION_MODE.equals(propName))
            setCompletionMode(CompletionMode.values()[(Integer)value]);
        if (PROP_TIMEOUT.equals(propName))
            setTimeout(Long.valueOf((String)value));
        else if (PROP_TIME_UNIT.equals(propName))
            setTimeUnit(TimeUnit.values()[(Integer)value]);
        else
            super.setPropertyValue(propName, value);
    }

    public long getTimeout() {
        return timeout;
    }
    
    public void setTimeout(long timeout) {
        this.timeout = timeout;
        firePropertyChange(PROP_NODE);
    }
    
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
    
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        firePropertyChange(PROP_NODE);
    }

    public CompletionMode getCompletionMode() {
        return completionMode;
    }

    public void setCompletionMode(CompletionMode completionMode) {
        this.completionMode = completionMode;
    }
}
