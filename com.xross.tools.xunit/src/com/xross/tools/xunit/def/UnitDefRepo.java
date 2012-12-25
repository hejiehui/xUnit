package com.xross.tools.xunit.def;

import java.util.HashMap;
import java.util.Map;

import com.xross.tools.xunit.Unit;
import com.xross.tools.xunit.UnitConfigure;

public class UnitDefRepo {
	private UnitConfigure configure;
	private Map<String, UnitDef> defRepo = new HashMap<String, UnitDef>();
	
	public UnitDefRepo(UnitConfigure configure){
		this.configure = configure;
	}
	
	public UnitConfigure getConfigure() {
		return configure;
	}
	
	public void put(String key, UnitDef unitDef){
		defRepo.put(key, unitDef);
	}
	
	public UnitDef getDef(String id){
		return defRepo.get(id);
	}
	
	public Unit getUnit(String id) throws Exception{
		return defRepo.get(id).getInstance();
	}
	
}
