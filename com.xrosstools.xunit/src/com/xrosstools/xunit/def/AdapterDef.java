package com.xrosstools.xunit.def;

import com.xrosstools.xunit.Adapter;
import com.xrosstools.xunit.Unit;

public class AdapterDef extends UnitDef{
	private UnitDef unitDef;
	
	public UnitDef getUnitDef() {
		return unitDef;
	}
	
	public void setUnitDef(UnitDef unitDef) {
		this.unitDef = unitDef;
	}
	
	protected Unit createInstance() throws Exception{
		Adapter adapter = (Adapter)super.createInstance();
		adapter.setUnit(getInstance(unitDef));
		return adapter;
	}
}
