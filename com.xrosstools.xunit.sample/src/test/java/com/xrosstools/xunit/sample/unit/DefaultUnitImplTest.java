package com.xrosstools.xunit.sample.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xrosstools.xunit.MapContext;
import com.xrosstools.xunit.impl.DefaultUnitImpl;

public class DefaultUnitImplTest {
    @Test
    public void testLocateByField() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "locatorFieldPrv");
        test.setUnitProperties(properties);
        assertEquals("locatorFieldPrv", test.locate(ctx));
        
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "locatorFieldGetter");
        test.setUnitProperties(properties);
        assertEquals("locatorFieldGetter", test.locate(ctx));

        properties.put(test.PROP_KEY_EVALUATE_FIELD, "locatorFieldPub");
        test.setUnitProperties(properties);
        assertEquals("locatorFieldPub", test.locate(ctx));
    }

    @Test
    public void testLocateByFieldForMapContext() throws Exception {
        MapContext ctx = new MapContext();

        ctx.put("testStringField", "abc");
        ctx.put("testBooleanField", true);

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "testStringField");
        test.setUnitProperties(properties);
        assertEquals("abc", test.locate(ctx));
    }

    @Test
    public void testLocateByMethod() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_METHOD, "locate");
        test.setUnitProperties(properties);
        assertEquals("locatorFieldPrv", test.locate(ctx));
    }
    
    @Test
    public void testValidateByField() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "validFieldPrv");
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));
        
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "validFieldGetter");
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));

        properties.put(test.PROP_KEY_EVALUATE_FIELD, "validFieldPub");
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));
    }

    @Test
    public void testValidateByFieldForMapContext() throws Exception {
        MapContext ctx = new MapContext();

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_FIELD, "testKeyName");
        
        ctx.put("testKeyName", true);
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));
        
        ctx.put("testKeyName", false);
        assertFalse(test.validate(ctx));
    }

    @Test
    public void testValidateByMethod() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_EVALUATE_METHOD, "validate");
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));
        
        properties.put(test.PROP_KEY_EVALUATE_METHOD, "validateString");
        test.setUnitProperties(properties);
        assertTrue(test.validate(ctx));
    }
    
    @Test
    public void testShowAppProp() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        Map<String, String> appProperties = new HashMap<>();
        properties.put(test.PROP_KEY_SHOW_APP_PROP, "app1,app2");
        
        appProperties.put("app1", "app1");
        appProperties.put("app2", "app2");
        
        test.setApplicationProperties(appProperties);
        test.setUnitProperties(properties);
        
        test.process(ctx);
    }

    @Test
    public void testShowFields() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_SHOW_FIELDS, "locatorFieldPrv,locatorFieldGetter,locatorFieldPub,validFieldPrv,validFieldGetter,validFieldPub");
        
        test.setUnitProperties(properties);
        
        test.process(ctx);
    }
    
    @Test
    public void testShowFieldsForMapContext() throws Exception {
        MapContext ctx = new MapContext();

        ctx.put("testStringField", "abc");
        ctx.put("testBooleanField", true);

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_SHOW_FIELDS, "testStringField,testBooleanField");
        
        test.setUnitProperties(properties);
        
        test.process(ctx);
    }
    
    @Test
    public void testShowMessage() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_SHOW_MESSAGE, "message");
        
        test.setUnitProperties(properties);
        
        test.process(ctx);
    }

    @Test
    public void testValidateDefault() throws Exception {
        DefaultUnitTestContext ctx = new DefaultUnitTestContext(
                "locatorFieldPrv",
                "locatorFieldGetter",
                "locatorFieldPub",
                true,
                true,
                true
                );

        DefaultUnitImpl test = new DefaultUnitImpl(null);
        Map<String, String> properties = new HashMap<>();
        properties.put(test.PROP_KEY_VALIDATE_DEFAULT, "true");
        
        test.setUnitProperties(properties);
        
        assertTrue(test.validate(ctx));
        
        properties.put(test.PROP_KEY_VALIDATE_DEFAULT, "false");
        
        test.setUnitProperties(properties);
        
        assertFalse(test.validate(ctx));

    }
}
