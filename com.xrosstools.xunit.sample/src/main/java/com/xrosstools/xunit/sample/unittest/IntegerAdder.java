package com.xrosstools.xunit.sample.unittest;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;

public class IntegerAdder implements Processor {
    @Override
    public void process(Context ctx) {
        IntegerContext intCtx = (IntegerContext)ctx;
        
        intCtx.sum = intCtx.a + intCtx.b; 
    }
    
    public static void main(String[] args) throws Exception {
        Processor adder = XunitFactory.load("integer_adder.xunit").getProcessor("Integer Adder");
        
        IntegerContext intCtx = new IntegerContext();
        
        intCtx.a = 1;
        intCtx.b = 2;
        
        adder.process(intCtx);
    }
}
