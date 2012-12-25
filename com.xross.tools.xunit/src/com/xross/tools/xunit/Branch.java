package com.xross.tools.xunit;

public interface Branch extends Unit{
	void setLocator(Locator locator);
	void add(String key, Unit unit);
}
