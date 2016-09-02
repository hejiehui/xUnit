package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;

public abstract class BaseCompositeImpl implements Processor, Converter {
	protected final void process(Unit unit, Context ctx){
		if(unit == null)
			return;
		
		if(unit instanceof Processor){
			((Processor)unit).process(ctx);
			return;
		}
		
		// For converter, we simply ignore the output
		if(unit instanceof Converter)
			((Converter)unit).convert(ctx);
	}

	protected final Context convert(Unit unit, Context inputCtx){
		if(unit == null)
			return inputCtx;

		if(unit instanceof Converter)
			return ((Converter)unit).convert(inputCtx);

		// For processor, we simply return the input
		if(unit instanceof Processor)
			((Processor)unit).process(inputCtx);
		
		return inputCtx;
	}
}
