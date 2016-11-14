package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class TextProcessor implements Processor {

	@Override
	public void process(Context ctx) {
		TextContext tc = (TextContext)ctx;
		if(tc.getValue() == null)
			tc.setValue("null");
		System.out.println("Original context value is " + tc.getValue());
		tc.setValue("Processed: " + tc.getValue());
	}
}
