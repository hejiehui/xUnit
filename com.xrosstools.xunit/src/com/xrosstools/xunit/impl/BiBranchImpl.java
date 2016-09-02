package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.BiBranch;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;

public class BiBranchImpl extends BaseCompositeImpl implements BiBranch{
	private Validator validator;
	private Unit validUnit;
	private Unit invalidUnit;
	
	public void setValidator(Validator validator){
		this.validator = validator;
	}
	public void setValidUnit(Unit unit){
		this.validUnit = unit;
	}
	public void setInvalidUnit(Unit unit){
		this.invalidUnit = unit;
	}
	
	public void process(Context ctx){
		if(validator.validate(ctx))
			process(validUnit, ctx);
		else 
			process(invalidUnit, ctx);
	}
	
	public Context convert(Context ctx){
		if(validator.validate(ctx))
			return convert(validUnit, ctx);
		else 
			return convert(invalidUnit, ctx);
	}
}
