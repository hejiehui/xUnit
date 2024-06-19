package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.XunitFactory;
import com.xrosstools.xunit.sample.spring.AppConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;


public class UnitMethodSpringSupportTest {
    static XunitFactory factory;
    @BeforeClass
    public static void setup() throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        factory  = XunitFactory.load("unit_method_spring_support.xunit");
    }

    @Test
    public void testProcessor() throws Exception {
        TextContext t = new TextContext("123");
        factory.getProcessor("processor").process(t);
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
