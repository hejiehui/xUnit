package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.Adapter;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Decorator;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;
import com.xrosstools.xunit.def.UnitDef;

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
