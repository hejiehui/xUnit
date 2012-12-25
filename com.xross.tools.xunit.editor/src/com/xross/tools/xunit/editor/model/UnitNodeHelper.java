package com.xross.tools.xunit.editor.model;

import java.util.ArrayList;
import java.util.List;

import com.xross.tools.xunit.BehaviorType;

public class UnitNodeHelper implements UnitConstants {
	private UnitNodeDiagram diagram;
	public UnitNodeHelper(UnitNodeDiagram diagram){
		this.diagram = diagram;
	}
	
	public String[] getReferenceNames(BehaviorType type, String excluded){
		List<String> names = new ArrayList<String>();
		excluded = getValue(excluded);
		names.add(EMPTY_VALUE);
		for(UnitNode unit: diagram.getUnits()){
			String name = unit.getName();
			if(unit.getType() != type || !isValid(name))
				continue;
			if(!name.equals(excluded))
				names.add(unit.getName());
		}
		return names.toArray(new String[names.size()]);
	}
	
	public String[] getReferenceNames(BehaviorType type, StructureType structureType, String excluded){
		List<String> names = new ArrayList<String>();
		excluded = getValue(excluded);
		names.add(EMPTY_VALUE);
		for(UnitNode unit: diagram.getUnits()){
			String name = unit.getName();
			if(unit.getType() != type || !isValid(name))
				continue;
			if(name.equals(excluded))
				continue;
			if(!(unit instanceof StructurePrimaryNode))
				continue;
			if(isValid(unit.getReferenceName()))
				continue;
			if(unit.getStructureType() == structureType)
				names.add(unit.getName());
		}
		return names.toArray(new String[names.size()]);
	}
	
	public String getValue(String value){
		return value == null? EMPTY_VALUE : value;
	}

	private boolean isValid(String value){
		if(value == null)
			return false;
		
		return value.trim().length() > 0;
	}

	public int getIndex(String[] values, String value){
		value = getValue(value);

		for(int i = 0; i< values.length; i++)
			if(value.equals(values[i]))
				return i;
		return 0;
	}
}
