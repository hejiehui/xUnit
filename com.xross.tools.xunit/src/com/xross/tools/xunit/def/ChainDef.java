package com.xross.tools.xunit.def;

import java.util.ArrayList;
import java.util.List;

import com.xross.tools.xunit.Chain;
import com.xross.tools.xunit.Unit;
import com.xross.tools.xunit.impl.ChainImpl;

public class ChainDef extends UnitDef{
	private List<UnitDef> unitDefs = new ArrayList<UnitDef>();

	public List<UnitDef> getUnitDefs() {
		return unitDefs;
	}

	public void addUnitDef(UnitDef unitDef) {
		unitDefs.add(unitDef);
	}
	
	protected Unit createDefault(){
		return new ChainImpl();
	}

	protected Unit createInstance() throws Exception{
		Chain chain = (Chain)super.createInstance();
		
		for(UnitDef def : unitDefs)
			chain.add(getInstance(def));
		
		return chain;
	}
}
