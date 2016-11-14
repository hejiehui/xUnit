package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;

public class TextContext implements Context{
	private String value;

	public TextContext(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
