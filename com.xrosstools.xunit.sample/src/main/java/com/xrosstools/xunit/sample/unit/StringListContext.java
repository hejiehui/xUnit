package com.xrosstools.xunit.sample.unit;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xunit.Context;

public class StringListContext implements Context {
    private List<String> list = new ArrayList<String>();
    
    public void add(String value) {
        list.add(value);
    }
    
    public List<String> getList() {
        return list;
    }
}
