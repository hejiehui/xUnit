package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Adapter;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;

public class TextAdapter implements Adapter, Converter{
	private Unit unit;
	@Override
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public Context convert(Context inputCtx) {
		((Processor)unit).process(inputCtx);
		return new IntegerContext(((TextContext)inputCtx).getValue().length());
	}

}
