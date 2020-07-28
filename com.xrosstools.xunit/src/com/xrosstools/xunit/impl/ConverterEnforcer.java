package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Unit;

public class ConverterEnforcer implements Converter {
    private Converter converter;

    public ConverterEnforcer(Unit unit) {
        converter = (Converter)unit;
    }

    @Override
    public Context convert(Context inputCtx) {
        return converter.convert(inputCtx);
    }
}
