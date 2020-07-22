package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class IntergerProcessor implements Processor {

	@Override
	public void process(Context ctx) {
		IntegerContext intCtx = (IntegerContext)ctx;
		intCtx.add();
	}
}
