package com.xrosstools.xunit.sample.spring;

import com.xrosstools.xunit.*;
import com.xrosstools.xunit.sample.unit.IntegerContext;
import com.xrosstools.xunit.sample.unit.TextContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public class UnitMethodSpringSupport implements UnitDefinitionAware, ApplicationPropertiesAware, UnitPropertiesAware {
    private Map<String, String> unitProperties;
    private Map<String, String> appProperties;
    private UnitDefinition unitDef;

    @Override
    public void setApplicationProperties(Map<String, String> properties) {
        this.appProperties = properties;
    }

    @Override
    public void setUnitDefinition(UnitDefinition unitDef) {
        this.unitDef = unitDef;
    }

    @Override
    public void setUnitProperties(Map<String, String> properties) {
        this.unitProperties = properties;
    }

    public boolean validateMethod(Context ctx) {
        return Boolean.valueOf(unitProperties.get("booleanValue"));
    }

    public String locateMethod(Context ctx) {
        return unitProperties.get("stringValue");
    }

    public void processMethod(Context ctx) {
        TextContext txt =(TextContext)ctx;
        txt.setValue(unitProperties.get("stringValue"));
    }

    public Context convertMethod(Context ctx) {
        return new IntegerContext(Integer.valueOf(unitProperties.get("intValue")));
    }

    public boolean validateMethodApp(Context ctx) {
        return Boolean.valueOf(appProperties.get("booleanValue"));
    }

    public String locateMethodApp(Context ctx) {
        return appProperties.get("stringValue");
    }

    public void processMethodApp(Context ctx) {
        TextContext txt =(TextContext)ctx;
        txt.setValue(appProperties.get("stringValue"));
    }

    public Context convertMethodApp(Context ctx) {
        return new IntegerContext(Integer.valueOf(appProperties.get("intValue")));
    }
}
