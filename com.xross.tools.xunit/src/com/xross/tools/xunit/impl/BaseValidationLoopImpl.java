package com.xross.tools.xunit.impl;

import com.xross.tools.xunit.BaseValidationLoop;
import com.xross.tools.xunit.Unit;
import com.xross.tools.xunit.Validator;

public abstract class BaseValidationLoopImpl extends BaseCompositeImpl implements BaseValidationLoop {
	protected Validator validator;
	protected Unit unit;
	
	public void setValidator(Validator validator){
		this.validator = validator;
	}
	
	public void setUnit(Unit unit){
		this.unit = unit;
	}	
}
