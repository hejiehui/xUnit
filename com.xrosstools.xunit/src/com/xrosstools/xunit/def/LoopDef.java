package com.xrosstools.xunit.def;

import com.xrosstools.xunit.BaseValidationLoop;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;
import com.xrosstools.xunit.impl.PostValidationLoopImpl;
import com.xrosstools.xunit.impl.PreValidationLoopImpl;

public class LoopDef extends UnitDef{
	private boolean whileLoop;
	private ValidatorDef validatorDef;
	private UnitDef unitDef;
	
	public LoopDef(boolean whileLoop) {
		this.whileLoop = whileLoop;
	}

	public ValidatorDef getValidatorDef() {
		return validatorDef;
	}
	
	public void setValidatorDef(ValidatorDef validatorDef) {
		this.validatorDef = validatorDef;
	}
	
	public UnitDef getUnitDef() {
		return unitDef;
	}
	
	public void setUnitDef(UnitDef unitDef) {
		this.unitDef = unitDef;
	}

	protected Unit createDefault(){
		return whileLoop ?
				new PreValidationLoopImpl() : 
					new PostValidationLoopImpl();
	}

	protected Unit createInstance() throws Exception{
		BaseValidationLoop loop = (BaseValidationLoop)super.createInstance();
		
		loop.setValidator((Validator)getInstance(validatorDef));
		loop.setUnit(getInstance(unitDef));
		
		return (Processor)loop;
	}
}
