package com.xrosstools.xunit.sample;

import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;
import com.xrosstools.xunit.sample.unit.TextContext;

public class ReferenceTest {
    public static void main(String[] args) {
        try {
            XunitFactory f = XunitFactory.load("reference.xunit");

            Processor p = f.getProcessor("chain1");
            TextContext ctx = new TextContext("Test");
            p.process(ctx);
            System.out.println(ctx.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
