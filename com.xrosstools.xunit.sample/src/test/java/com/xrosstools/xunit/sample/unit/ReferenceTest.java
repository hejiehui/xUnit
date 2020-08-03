package com.xrosstools.xunit.sample.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;

public class ReferenceTest {
    @Test
    public void testReference() throws Exception {
        String[] exp = new String[] {
                "Name: reference.xunit:chain2:unit1",
                "Name: proj/project.xunit:processor",
                "Name: reference.xunit:chain2:unit3",
                "Name: proj/modulet.xunit:processor",
                "Name: proj/module/sumodule/submodule.xunit:processor"
        };
        Processor p = XunitFactory.load("reference.xunit").getProcessor("chain1");
        StringListContext ctx = new StringListContext();
        p.process(ctx);
        int i = 0;
        for(String v: ctx.getList()) {
            System.out.println(v);
            assertEquals(exp[i++], v);
        }
    }

    @Test
    public void testProjectReference() throws Exception {
        Processor p = XunitFactory.load("proj/project.xunit").getProcessor("a chain");
        TextContext ctx = new TextContext("Test");
        p.process(ctx);
        assertEquals("processed: test", ctx.getValue());
    }
}
