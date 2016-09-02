package com.xrosstools.xunit.impl;

import com.xrosstools.xunit.BaseValidationLoop;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;

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
