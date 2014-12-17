package com.xross.tools.xunit.editor.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;

import com.xross.tools.xunit.BehaviorType;

public class UnitNodeHelper implements UnitConstants {
	private UnitNodeDiagram diagram;
	public UnitNodeHelper(UnitNodeDiagram diagram){
		this.diagram = diagram;
	}
	
	private String getTopLevelNodeName(EditPart curPart) {
		String excluded = null;
		while(curPart.getModel() != diagram) {
			if(curPart.getModel() instanceof UnitNode)
				excluded = ((UnitNode)curPart.getModel()).getName();
			curPart = curPart.getParent();
		}
		return getValue(excluded);
	}
	
	public String[] getReferenceNames(BehaviorType type, EditPart curPart){
		List<String> names = new ArrayList<String>();
		String excluded = getTopLevelNodeName(curPart);
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
	
	public String[] getReferenceNames(BehaviorType type, StructureType structureType, EditPart curPart){
		List<String> names = new ArrayList<String>();
		String excluded = getTopLevelNodeName(curPart);//getValue(excluded);
		names.add(EMPTY_VALUE);
		for(UnitNode unit: diagram.getUnits()){
			String name = unit.getName();
			if(unit.getType() != type || !isValid(name))
				continue;
			if(name.equals(excluded))
				continue;
			if(!(unit instanceof PrimaryNode))
				continue;
			if(isValid(unit.getReferenceName()))
				continue;
			/*
			if(unit.getStructureType() == structureType)
				names.add(unit.getName());
			*/
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
