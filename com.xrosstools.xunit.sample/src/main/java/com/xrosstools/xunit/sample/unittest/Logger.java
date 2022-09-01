package com.xrosstools.xunit.sample.unittest;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class Logger implements Processor {
    private int threshold;

    @Override
    public void process(Context ctx) {
        IntegerContext intCtx = (IntegerContext)ctx;

        if(intCtx.sum > threshold)
            System.out.println(intCtx.sum);
    }
}
