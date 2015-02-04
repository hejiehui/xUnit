package com.xross.tools.xunit.impl;

import java.util.HashMap;
import java.util.Map;

import com.xross.tools.xunit.Branch;
import com.xross.tools.xunit.Context;
import com.xross.tools.xunit.Locator;
import com.xross.tools.xunit.Unit;

public class BranchImpl extends BaseCompositeImpl implements Branch {
	private Locator locator;
	private Map<String, Unit> unitMap = new HashMap<String, Unit>();
	
	public void setLocator(Locator locator){
		this.locator = locator;
	}
	public void add(String key, Unit unit){
		unitMap.put(key, unit);
	}
	
	protected Unit locateUnit(Context ctx){
		String key = locator.locate(ctx);
		Unit unit = unitMap.get(key);
		if(unit != null)
			return unit;

		return unitMap.get(locator.getDefaultKey());
	}

	public void process(Context ctx){
		process(locateUnit(ctx), ctx);
	}
	
	public Context convert(Context inputCtx) {
		return convert(locateUnit(inputCtx), inputCtx);
	}
}
