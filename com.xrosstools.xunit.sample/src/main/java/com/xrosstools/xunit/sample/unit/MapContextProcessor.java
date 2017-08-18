package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.MapContext;
import com.xrosstools.xunit.Processor;

public class MapContextProcessor implements Processor {

    @Override
    public void process(Context arg0) {
        MapContext mc = (MapContext)arg0;
        System.out.println("contains key: " + mc.containsKey("key"));
        System.out.println("key=" + mc.get("key"));
        mc.put("key1", "abc");
        System.out.println("keyset: " + mc.keySet());
        System.out.println("entry set: " + mc.entrySet());
        System.out.println("contains key: " + mc.containsKey("key"));
        System.out.println("contains abc: " + mc.containsValue("abc"));
        System.out.println("Is empty: " + mc.isEmpty());
        System.out.println("Size: " + mc.size());
    }
}
