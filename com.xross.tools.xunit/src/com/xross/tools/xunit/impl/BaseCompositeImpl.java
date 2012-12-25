package com.xross.tools.xunit.impl;

import com.xross.tools.xunit.Context;
import com.xross.tools.xunit.Converter;
import com.xross.tools.xunit.Processor;
import com.xross.tools.xunit.Unit;

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
