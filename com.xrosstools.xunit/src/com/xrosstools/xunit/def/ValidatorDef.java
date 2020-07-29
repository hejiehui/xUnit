package com.xrosstools.xunit.def;

import com.xrosstools.xunit.UnitDefinition;

public class ValidatorDef extends UnitDef{
	private String validLabel;
	private String invalidLabel;
	
	public String getValidLabel() {
		return validLabel;
	}
	
	public void setValidLabel(String validLabel) {
		this.validLabel = validLabel;
	}
	
	public String getInvalidLabel() {
		return invalidLabel;
	}
	
	public void setInvalidLabel(String invalidLabel) {
		this.invalidLabel = invalidLabel;
	}
	
	protected UnitDefinition createDefinition() {
	    UnitDefinition ud = super.createDefinition();
	    ud.setValidLabel(validLabel);
	    ud.setInvalidLabel(invalidLabel);
	    return ud;
	}
}
