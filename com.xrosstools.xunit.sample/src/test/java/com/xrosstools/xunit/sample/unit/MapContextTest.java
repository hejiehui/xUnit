package com.xrosstools.xunit.sample.unit;

import org.junit.Test;

import com.xrosstools.xunit.MapContext;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;

public class MapContextTest {
    @Test
    public void testLocateByField() throws Exception {
        XunitFactory f = XunitFactory.load("reference.xunit");

        Processor p = f.getProcessor("Map Context Processor");
        MapContext ctx = new MapContext();
        ctx.put("key", "123");
        p.process(ctx);
    }
}
