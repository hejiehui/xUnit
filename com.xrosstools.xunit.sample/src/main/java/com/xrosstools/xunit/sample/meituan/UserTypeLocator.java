package com.xrosstools.xunit.sample.meituan;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Locator;

public class UserTypeLocator implements Locator {
    private String defaultKey;
    
    @Override
    public String locate(Context ctx) {
        return ((RewardContxt)ctx).getType().getLabel();
    }

    @Override
    public void setDefaultKey(String key) {
        defaultKey = key;
    }

    @Override
    public String getDefaultKey() {
        return defaultKey;
    }
}
