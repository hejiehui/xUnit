package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Decorator;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;

public abstract class DecoratorAdapter implements Processor, Converter, Validator, Locator, Decorator {
	private Unit unit;

	public final void setUnit(Unit unit){
		this.unit = unit;
	}
	
	public final void setDefaultKey(String key) {
		((Locator)unit).setDefaultKey(key);
	}

	public final String getDefaultKey() {
		return ((Locator)unit).getDefaultKey();
	}

	public final String locate(Context ctx){
		before(ctx);
		String key = ((Locator)unit).locate(ctx);
		after(ctx);
		return key;
	}

	public final boolean validate(Context ctx){
		before(ctx);
		boolean result = ((Validator)unit).validate(ctx);
		after(ctx);
		return result;
	}

	public final Context convert(Context inputCtx) {
		before(inputCtx);
		Context output = ((Converter)unit).convert(inputCtx);
		after(inputCtx);
		return output;
	}

	public final void process(Context ctx) {
		before(ctx);
		((Processor)unit).process(ctx);
		after(ctx);
	}
}