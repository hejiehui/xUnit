package com.xross.tools.xunit.def;

import com.xross.tools.xunit.BiBranch;
import com.xross.tools.xunit.Unit;
import com.xross.tools.xunit.Validator;
import com.xross.tools.xunit.impl.BiBranchImpl;


public class BiBranchDef extends UnitDef{
	private ValidatorDef validatorDef;
	private UnitDef validUnitDef;
	private UnitDef invalidUnitDef;
	
	public ValidatorDef getValidatorDef() {
		return validatorDef;
	}
	
	public void setValidatorDef(ValidatorDef validatorDef) {
		this.validatorDef = validatorDef;
	}
	
	public UnitDef getValidUnitDef() {
		return validUnitDef;
	}
	
	public void setValidUnitDef(UnitDef validUnitDef) {
		this.validUnitDef = validUnitDef;
	}
	
	public UnitDef getInvalidUnitDef() {
		return invalidUnitDef;
	}
	
	public void setInvalidUnitDef(UnitDef invalidUnitDef) {
		this.invalidUnitDef = invalidUnitDef;
	}
	
	protected Unit createDefault(){
		return new BiBranchImpl();
	}

	protected Unit createInstance() throws Exception{
		BiBranch biBranch = (BiBranch)super.createInstance();
		
		biBranch.setValidator((Validator)getInstance(validatorDef));
		biBranch.setValidUnit(getInstance(validUnitDef));
		biBranch.setInvalidUnit(getInstance(invalidUnitDef));
		
		return biBranch;
	}
}
