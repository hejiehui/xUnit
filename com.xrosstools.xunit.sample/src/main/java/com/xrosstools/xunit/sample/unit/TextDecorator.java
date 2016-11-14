package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.impl.DecoratorAdapter;

public class TextDecorator extends DecoratorAdapter {

	@Override
	public void before(Context ctx) {
		TextContext tc = (TextContext)ctx;
		
		tc.setValue("***" + tc.getValue());
	}

	@Override
	public void after(Context ctx) {
		IntegerContext tc = (IntegerContext)ctx;
		
		tc.setValue(tc.getValue() + 100);
	}

}
