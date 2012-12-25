package com.xross.tools.xunit.impl;

import java.util.ArrayList;
import java.util.List;

import com.xross.tools.xunit.Chain;
import com.xross.tools.xunit.Context;
import com.xross.tools.xunit.Unit;

public class ChainImpl extends BaseCompositeImpl implements Chain{
	private List<Unit> units = new ArrayList<Unit>();
	
	public void add(Unit unit){
		units.add(unit);
	}
	
	public void process(Context ctx){
		for(Unit unit: units)
			process(unit, ctx);
	}

	public Context convert(Context inputCtx){
		for(Unit unit: units)
			inputCtx = convert(unit, inputCtx);
		
		return inputCtx;
	}
}
