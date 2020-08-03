package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.UnitDefinition;
import com.xrosstools.xunit.UnitDefinitionAware;

public class UnitDefinitionAwareProcessor implements Processor, UnitDefinitionAware {
    private UnitDefinition unitDef;
    @Override
    public void setUnitDefinition(UnitDefinition unitDef) {
        this.unitDef = unitDef;
    }

    @Override
    public void process(Context ctx) {
        StringListContext listCtx = (StringListContext)ctx;
        listCtx.add(String.format("Name: %s", unitDef.getName()));
    }

}
