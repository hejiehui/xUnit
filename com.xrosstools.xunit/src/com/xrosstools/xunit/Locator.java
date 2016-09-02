package com.xrosstools.xunit;

public interface Locator extends Unit{
	String locate(Context ctx);
	void setDefaultKey(String key);
	String getDefaultKey();
}
