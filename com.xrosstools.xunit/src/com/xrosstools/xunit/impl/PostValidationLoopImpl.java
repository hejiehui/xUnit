package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Context;

public class PostValidationLoopImpl extends BaseValidationLoopImpl {
	public void process(Context ctx){
		do{
			process(unit, ctx);
		}while(validator.validate(ctx));
	}
	
	public Context convert(Context inputCtx){
		do{
			inputCtx = convert(unit, inputCtx);
		}while(validator.validate(inputCtx));
		return inputCtx;
	}
}
