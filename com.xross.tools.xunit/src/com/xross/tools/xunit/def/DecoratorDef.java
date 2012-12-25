package com.xross.tools.xunit.def;

import com.xross.tools.xunit.Decorator;
import com.xross.tools.xunit.Unit;

public class DecoratorDef extends UnitDef{
	private UnitDef unitDef;
	
	public UnitDef getUnitDef() {
		return unitDef;
	}
	public void setUnitDef(UnitDef unitDef) {
		this.unitDef = unitDef;
	}
	
	protected Unit createInstance() throws Exception{
		Decorator decorator = (Decorator)super.createInstance();
		decorator.setUnit(getInstance(unitDef));
		return decorator;
	}	
}
