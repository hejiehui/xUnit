package com.xrosstools.xunit.impl;

import java.lang.reflect.Field;
import java.util.Map;

import com.xrosstools.xunit.Adapter;
import com.xrosstools.xunit.ApplicationPropertiesAware;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Decorator;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.UnitPropertiesAware;
import com.xrosstools.xunit.Validator;
import com.xrosstools.xunit.def.UnitDef;

/**
 * This unit implementation is just for quick verifying system.
 * @author jiehe
 *
 */
public class DefaultUnitImpl implements Processor, Converter, Validator, Locator, Decorator, Adapter, ApplicationPropertiesAware, UnitPropertiesAware {
    public static final String PROP_KEY_SHOW_MESSAGE = "showMessage";
    public static final String PROP_KEY_SHOW_FIELDS = "showFields";
    public static final String PROP_KEY_SHOW_APP_PROP = "showApplicationProperties";
    
    public static final String PROP_KEY_EVALUATE_FIELD = "evaluateField";
    public static final String PROP_KEY_VALIDATE_DEFAULT = "validateDefault";
    
	private String messageToShow;
	private String[] fieldsToShow;
	private String[] applicationPropertiesToShow;
    
    private String evaluateFieldName;
    private boolean validateDefault;
    
    private Map<String, String> appProperties;
    
	private String key;

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
        
        validateDefault = properties.containsKey(PROP_KEY_VALIDATE_DEFAULT) ? Boolean.parseBoolean(properties.get(PROP_KEY_VALIDATE_DEFAULT)) : true;
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
		this.key = key;
	}
	
	public String getDefaultKey(){
		return key;
	}

	public String locate(Context ctx){
	    if(evaluateFieldName == null)
	        return key;

	    try {
            Field field = ctx.getClass().getField(evaluateFieldName);
            return field.get(ctx).toString();
        } catch (Throwable e) {
            throw new RuntimeException("Can not evaluate field : " + evaluateFieldName, e);
        }
	}

	public boolean validate(Context ctx){
        if(evaluateFieldName == null)
            return validateDefault;

        try {
            Field field = ctx.getClass().getField(evaluateFieldName);
            return Boolean.parseBoolean(field.get(ctx).toString());
        } catch (Throwable e) {
            throw new RuntimeException("Can not evaluate field : " + evaluateFieldName, e);
        }
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
	
	private void displayMessage(Context ctx) {
	    showMessage();
	    showFields(ctx);
	    showAppProperties();
	}
	
	private void showMessage() {
        if(messageToShow != null)
            System.out.println(messageToShow);	    
	}
	
    private void showFields(Context ctx) {
        if(fieldsToShow == null)
            return;
        
        Class<?> clazz = ctx.getClass();
        for(String fieldName: fieldsToShow) {
            try {
                System.out.println(String.format("%s: %s", fieldName, clazz.getField(fieldName).get(ctx).toString()));
            } catch (Throwable e) {
                throw new RuntimeException("Can not display field valuen for field: " + fieldName, e);
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
}
