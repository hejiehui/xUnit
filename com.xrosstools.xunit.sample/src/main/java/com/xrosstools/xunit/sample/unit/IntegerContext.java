package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;

public class IntegerContext implements Context {
	private Integer value;
	
	public IntegerContext(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
