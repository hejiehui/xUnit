package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

public class TextShortenProcessor implements Processor {

	@Override
	public void process(Context ctx) {
		TextContext tc = (TextContext)ctx;
		tc.setValue(tc.getValue().substring(1));
	}
}
