package com.xross.tools.xunit.impl;

import com.xross.tools.xunit.Adapter;
import com.xross.tools.xunit.Context;
import com.xross.tools.xunit.Converter;
import com.xross.tools.xunit.Decorator;
import com.xross.tools.xunit.Locator;
import com.xross.tools.xunit.Processor;
import com.xross.tools.xunit.Unit;
import com.xross.tools.xunit.Validator;
import com.xross.tools.xunit.def.UnitDef;

/**
 * This unit implementation is just for quick verifying system.
 * @author jiehe
 *
 */
public class DefaultUnitImpl implements Processor, Converter, Validator, Locator, Decorator, Adapter {
	private UnitDef unitDef;
	
	private String key;

	public DefaultUnitImpl(UnitDef unitDef){
		this.unitDef = unitDef;
	}
	
	public void setDefaultKey(String key){
		this.key = key;
	}
	
	public String getDefaultKey(){
		return key;
	}

	public String locate(Context ctx){
		return key;
	}

	public boolean validate(Context ctx){
		return true;
	}

	public Context convert(Context inputCtx) {
		return inputCtx;
	}

	public void process(Context ctx) {
	}


	public void setUnit(Unit unit) {
	}

	public void before(Context ctx) {
	}

	public void after(Context ctx) {
	}
}
