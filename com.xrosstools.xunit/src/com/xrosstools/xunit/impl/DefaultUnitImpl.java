package com.xrosstools.xunit.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xunit.Adapter;
import com.xrosstools.xunit.ApplicationPropertiesAware;
import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.CompletionMode;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Decorator;
import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.TaskType;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.UnitDefinition;
import com.xrosstools.xunit.UnitDefinitionAware;
import com.xrosstools.xunit.UnitPropertiesAware;
import com.xrosstools.xunit.Validator;
import com.xrosstools.xunit.def.UnitDef;

/**
 * This unit implementation is just for quick verifying system.
 * @author jiehe
 *
 */
public class DefaultUnitImpl implements Processor, Converter, Validator, Locator, Dispatcher, Decorator, Adapter, ApplicationPropertiesAware, UnitPropertiesAware, UnitDefinitionAware {
    /**
     * public static final String constant with name start with PROP_KEY will be displayed 
     * in "Set property" context menu
     */
    public static final String PROP_KEY_SHOW_MESSAGE = "showMessage";
    public static final String PROP_KEY_SHOW_FIELDS = "showFields";
    public static final String PROP_KEY_SHOW_APP_PROP = "showApplicationProperties";
    public static final String PROP_KEY_SHOW_UNIT_DEFINITION = "showUnitDefinition";
    
    // The method take higher priority
    public static final String PROP_KEY_EVALUATE_METHOD = "evaluateMethod";
    public static final String PROP_KEY_EVALUATE_FIELD = "evaluateField";

    public static final String PROP_KEY_VALIDATE_DEFAULT = "validateDefault";
    
	private String messageToShow;
	private String[] fieldsToShow;
	private String[] applicationPropertiesToShow;
    
    private String evaluateFieldName;
    private String evaluateMethodName;
    
    // Default is true
    private boolean validateDefault = true;
    
    private boolean showUnitDefinition = false;
    
    private Map<String, String> appProperties;
    
    private UnitDefinition unitDef;
    
	private String defaultKey;

	private CompletionMode mode;
    private long timeout;
    private TimeUnit timeUnit;


	public DefaultUnitImpl(UnitDef unitDef){
	}
	
    public void setApplicationProperties(Map<String, String> properties) {
        this.appProperties = properties;
    }
    
    public void setUnitProperties(Map<String, String> properties) {
        messageToShow = properties.get(PROP_KEY_SHOW_MESSAGE);
        fieldsToShow = parse(properties.get(PROP_KEY_SHOW_FIELDS));
        applicationPropertiesToShow = parse(properties.get(PROP_KEY_SHOW_APP_PROP));

        evaluateFieldName = properties.get(PROP_KEY_EVALUATE_FIELD);
        if(evaluateFieldName!= null)
            evaluateFieldName = evaluateFieldName.trim();
        
        evaluateMethodName = properties.get(PROP_KEY_EVALUATE_METHOD);
        if(evaluateMethodName!= null)
            evaluateMethodName = evaluateMethodName.trim();

        validateDefault = properties.containsKey(PROP_KEY_VALIDATE_DEFAULT) ? Boolean.parseBoolean(properties.get(PROP_KEY_VALIDATE_DEFAULT)) : true;
        
        showUnitDefinition = properties.containsKey(PROP_KEY_SHOW_UNIT_DEFINITION) ? Boolean.parseBoolean(properties.get(PROP_KEY_SHOW_UNIT_DEFINITION)) : false;
    }

    @Override
    public void setUnitDefinition(UnitDefinition unitDef) {
        this.unitDef = unitDef;
    }

    private String[] parse(String value) {
        if(value == null || value.trim().length() == 0)
            return null;
        
        String[] values = value.split(",");
        for(int i = 0; i < values.length; i++)
            values[i] = values[i].trim();
        
        return values;
    }
            
	public void setDefaultKey(String key){
		this.defaultKey = key;
	}
	
	public String getDefaultKey(){
		return defaultKey;
	}

	public String locate(Context ctx){
		if(evaluateFieldName == null && evaluateMethodName == null)
	        return defaultKey;

        return getValue(ctx).toString();
	}

	public boolean validate(Context ctx){
        if(evaluateFieldName == null && evaluateMethodName == null)
            return validateDefault;

        Object value = getValue(ctx);
        if(value != null && value instanceof Boolean)
        	return (Boolean)value;
        return Boolean.parseBoolean(value.toString());
	}

    @Override
    public void setCompletionMode(CompletionMode mode) {
        this.mode = mode;
    }

    @Override
    public CompletionMode getCompletionMode() {
        return mode;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public Map<String, Context> dispatch(Context inputCtx, Map<String, TaskType> taskInfoMap) {
        Map<String, Context> ctxMap = new HashMap<String, Context>();
        // This is just an quick and dirty implementation, concurrent accessing risks are ignored
        for(String taskId: taskInfoMap.keySet())
            ctxMap.put(taskId, inputCtx);
        return ctxMap;
    }

    @Override
    public Context merge(Context inputCtx, List<Context> results) {
        return results.get(0);
    }

    @Override
    public Context onTaskError(Context inputCtx, Exception exception) {
        return inputCtx;
    }

    @Override
    public Context onInvokeError(Map<String, Context> dispatchedCtx, Exception exception) {
        return dispatchedCtx.values().iterator().next();
    }
	
	public Context convert(Context inputCtx) {
	    displayMessage(inputCtx);
		return inputCtx;
	}

	public void process(Context ctx) {
	    displayMessage(ctx);
	}


	public void setUnit(Unit unit) {
	}

	public void before(Context ctx) {
	}

	public void after(Context ctx) {
	}
	
	private Object getValueFromMethod(Context ctx, String methodName) {
        try {
            Method method = ctx.getClass().getDeclaredMethod(evaluateMethodName, new Class[0]);
            method.setAccessible(true);
            return method.invoke(ctx, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException("Can not invoke method: " + evaluateMethodName, e);
        }
    }
	
	private Object getValueFromField(Context ctx, String fieldName) {
	    try {
            Field field = ctx.getClass().getDeclaredField(fieldName);
            
            // Try getter of the field first
            StringBuilder sb = new StringBuilder();
            sb.append(fieldName);
            if (Character.isLowerCase(sb.charAt(0))) {
                if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                }
            }
            if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                sb.insert(0, "is");
            } else {
                sb.insert(0, "get");
            }
            
            Method method = null;
            try{
                method = ctx.getClass().getDeclaredMethod(sb.toString(), new Class[0]);
            } catch (NoSuchMethodException e) {
            }
            
            if(method != null) {
                  method.setAccessible(true);
                  return method.invoke(ctx, new Object[0]);
            } else {
                field.setAccessible(true);
                return field.get(ctx);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Can not evaluate field: " + fieldName, e);
        }
	}

	
	private Object getValue(Context ctx) {
        if(evaluateMethodName != null)
            return getValueFromMethod(ctx, evaluateMethodName);
        
        if(evaluateFieldName != null)
            return getValueFromField(ctx, evaluateFieldName);
        
        return null;
    }
	   
	private void displayMessage(Context ctx) {
	    showMessage();
	    showFields(ctx);
	    showAppProperties();
	    showUnitDefinition();
	}
	
	private void showMessage() {
        if(messageToShow != null)
            System.out.println(messageToShow);	    
	}
	
    private void showFields(Context ctx) {
        if(fieldsToShow == null)
            return;
        
        for(String fieldName: fieldsToShow) {
            try {
                String value = String.valueOf(getValueFromField(ctx, fieldName));
                System.out.println(String.format("%s: %s", fieldName, value));
            } catch (Throwable e) {
                throw new RuntimeException("Can not display field value for field: " + fieldName, e);
            }
        }
    }
    
    private void showAppProperties() {
        if(applicationPropertiesToShow == null)
            return;
        
        for(String propName: applicationPropertiesToShow) {
            System.out.println(String.format("%s: %s", propName, appProperties.get(propName)));
        }
    }
    
    private void showUnitDefinition() {
        if(!showUnitDefinition)
            return;
        
        System.out.println(String.format("%s: %s", "name", unitDef.getName()));
        System.out.println(String.format("%s: %s", "type", unitDef.getType()));
        System.out.println(String.format("%s: %s", "description", unitDef.getDescription()));
        System.out.println(String.format("%s: %s", "singleton", unitDef.isSingleton()));
        System.out.println(String.format("%s: %s", "className", unitDef.getClassName()));
        System.out.println(String.format("%s: %s", "key", unitDef.getKey()));
        System.out.println(String.format("%s: %s", "taskType", unitDef.getTaskType()));
        
        if(BehaviorType.validator == unitDef.getType()) {
            System.out.println(String.format("%s: %s", "validLabel", unitDef.getValidLabel()));
            System.out.println(String.format("%s: %s", "invalidLabel", unitDef.getInvalidLabel()));
        }
        
        if(BehaviorType.locator == unitDef.getType()) {
            System.out.println(String.format("%s: %s", "defaultKey", defaultKey));
        }
        
        if(BehaviorType.dispatcher == unitDef.getType()) {
            System.out.println(String.format("%s: %s", "completionMode()", mode));
            System.out.println(String.format("%s: %s", "timeout", timeout));
            System.out.println(String.format("%s: %s", "timeUnit", timeUnit));
        }
    }
}
