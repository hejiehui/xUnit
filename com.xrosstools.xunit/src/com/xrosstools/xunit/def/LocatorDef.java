package com.xrosstools.xunit.def;

import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Unit;


public class LocatorDef extends UnitDef{
	private String defaultKey;

	public String getDefaultKey() {
		return defaultKey;
	}

	public void setDefaultKey(String defaultKey) {
		this.defaultKey = defaultKey;
	}
	
	protected Unit createInstance() throws Exception{
		Locator locator = (Locator)super.createInstance();
		
		locator.setDefaultKey(defaultKey);
		
		return locator;
	}
}
