package com.xrosstools.xunit.def;

import com.xrosstools.xunit.Decorator;
import com.xrosstools.xunit.Unit;

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
