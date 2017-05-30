package com.xrosstools.xunit.editor.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.gef.EditPart;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.editor.io.UnitNodeDiagramFactory;

public class UnitNodeHelper implements UnitConstants {
	private UnitNodeDiagram diagram;
	public UnitNodeHelper(UnitNodeDiagram diagram){
		this.diagram = diagram;
	}
	
	public String[] getReferenceNames(UnitNode node, EditPart curPart){
		UnitNodeDiagramFactory diagramFactory = new UnitNodeDiagramFactory();
		
		if(!node.isValid(node.getModuleName()) || node.getModuleName().equals(diagram.getFileName())) {
			String excluded = getTopLevelNodeName(curPart);
			if(node instanceof CompositeUnitNode)
				return getReferenceNames(node.getType(), ((CompositeUnitNode)node).getStructureType(), excluded);
			else
				return getReferenceNames(node.getType(), excluded);
		}
		
		try {
    		FileInputStream is = new FileInputStream(new File(node.getModuleName()));
    		UnitNodeDiagram diagram = diagramFactory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is));
    		diagram.setFileName(node.getModuleName());
    		UnitNodeHelper helper = new UnitNodeHelper(diagram);
    		if(node instanceof CompositeUnitNode)
    			return helper.getReferenceNames(node.getType(), ((CompositeUnitNode)node).getStructureType(), null);
    		else
    			return helper.getReferenceNames(node.getType(), null);
		} catch (Throwable e) {
//			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Can not locate " + node.getModuleName(), e.getMessage());
			return new String[]{};
		}
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
	
	private String[] getReferenceNames(BehaviorType type, String excluded){
		List<String> names = new ArrayList<String>();

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
	
	private String[] getReferenceNames(BehaviorType type, StructureType structureType, String excluded){
		List<String> names = new ArrayList<String>();

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
