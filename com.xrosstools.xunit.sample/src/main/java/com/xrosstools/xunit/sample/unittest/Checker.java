package com.xrosstools.xunit.sample.unittest;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class Checker implements Processor {
    @Override
    public void process(Context ctx) {
        IntegerContext intCtx = (IntegerContext)ctx;
        
        if(intCtx.a < 0 || intCtx.b < 0)
            throw new IllegalArgumentException("Only positive number allowed!");
    }
}
