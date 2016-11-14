package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;

public class TextConverter implements Converter {

	@Override
	public Context convert(Context inputCtx) {
		TextContext tc = (TextContext)inputCtx;
		System.out.println("Input value: " + tc.getValue());
		return new IntegerContext(tc.getValue() == null?0:tc.getValue().length());
	}
}
