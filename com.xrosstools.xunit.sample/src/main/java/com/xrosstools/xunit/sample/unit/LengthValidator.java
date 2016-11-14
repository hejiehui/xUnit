package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Validator;

public class LengthValidator implements Validator {

	@Override
	public boolean validate(Context ctx) {
		TextContext tc = (TextContext)ctx;
		return tc.getValue().length() > 10;
	}
}
