package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.XunitFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UnitMethodSupportTest {
    static XunitFactory factory;
    @BeforeClass
    public static void setup() throws Exception {
        factory  = XunitFactory.load("unit_method_support.xunit");
    }

    @Test
    public void testProcessor() throws Exception {
        TextContext t = new TextContext("123");
        factory.getProcessor("processor").process(t);
        assertEquals("abc", t.getValue());
    }

    @Test
    public void testProcessorPrivate() throws Exception {
        TextContext t = new TextContext("123");
        factory.getProcessor("private processor").process(t);
        assertEquals("abc", t.getValue());
    }

    @Test
    public void testConverter() throws Exception {
        TextContext t = new TextContext("abc");
        IntegerContext ctx = (IntegerContext)factory.getConverter("converter").convert(t);
        assertEquals(123L, (long)ctx.getValue());
    }

    @Test
    public void testValidator() throws Exception {
        TextContext t = new TextContext("123");
        factory.getProcessor("validator").process(t);
        assertEquals("true", t.getValue());
    }

    @Test
    public void testLocator() throws Exception {
        TextContext t = new TextContext("123");
        IntegerContext ctx = (IntegerContext)factory.getConverter("locator").convert(t);
        assertEquals(100L, (long)ctx.getValue());
    }
}
