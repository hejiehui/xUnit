package com.xross.tools.xunit.impl;

import com.xross.tools.xunit.Context;

public class PreValidationLoopImpl extends BaseValidationLoopImpl {
	public void process(Context ctx){
		while(validator.validate(ctx))
			process(unit, ctx);
	}

	public Context convert(Context inputCtx){
		while(validator.validate(inputCtx))
			inputCtx = convert(unit, inputCtx);
		return inputCtx;
	}
}
