package com.xrosstools.xunit.sample.unit;

import java.util.Map;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.UnitPropertiesAware;

public class PropertyAwareProcessor implements Processor, UnitPropertiesAware {
    public static final String PROP_KEY_ATTRIB = "My test";

    @Override
    public void process(Context ctx) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setUnitProperties(Map<String, String> properties) {
        // TODO Auto-generated method stub
        
    }
    
}
