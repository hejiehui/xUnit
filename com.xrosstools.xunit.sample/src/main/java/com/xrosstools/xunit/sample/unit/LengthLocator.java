package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Locator;

public class LengthLocator implements Locator {
	private String defaultKey;
	@Override
	public String locate(Context ctx) {
		TextContext tc = (TextContext)ctx;
		
		if(tc.getValue() == null)
			return null;
		
		if(tc.getValue().length() < 10)
			return "<10";
		
		if(tc.getValue().length() < 20)
			return "10 - 20";
		
		return ">20";
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
