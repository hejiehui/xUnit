package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;

public class ProcessorEnforcer implements Processor {
    private Processor processor;

    public ProcessorEnforcer(Unit unit) {
        processor = (Processor)unit;
    }

    @Override
    public void process(Context ctx) {
        processor.process(ctx);
    }
}
