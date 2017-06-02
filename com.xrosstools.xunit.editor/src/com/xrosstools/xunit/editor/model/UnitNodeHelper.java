package com.xrosstools.xunit.editor.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.editor.io.UnitNodeDiagramFactory;

public class UnitNodeHelper implements UnitConstants {
	private UnitNodeDiagram diagram;
	public UnitNodeHelper(UnitNodeDiagram diagram){
		this.diagram = diagram;
	}
	
	public String[] getReferenceNames(UnitNode node, EditPart curPart){
		if(!node.isValid(node.getModuleName()) || node.getModuleName().equals(diagram.getFilePath())) {
			String excluded = getTopLevelNodeName(curPart);
			if(node instanceof CompositeUnitNode)
				return getReferenceNames(node.getType(), ((CompositeUnitNode)node).getStructureType(), excluded);
			else
				return getReferenceNames(node.getType(), excluded);
		}
		
		try {
    		UnitNodeDiagram diagram = load(node.getModuleName());
    		UnitNodeHelper helper = new UnitNodeHelper(diagram);
    		if(node instanceof CompositeUnitNode)
    			return helper.getReferenceNames(node.getType(), ((CompositeUnitNode)node).getStructureType(), null);
    		else
    			return helper.getReferenceNames(node.getType(), null);
		} catch (Throwable e) {
			e.printStackTrace(System.err);
			return new String[]{};
		}
	}
	
	public boolean isFileExist(String moduleName) {
	    if(!isValid(moduleName))
	        return false;
	    
        if(!new File(moduleName).exists()) {
            IFile moduleFile = (IFile)diagram.getFilePath().getParent().findMember(moduleName);
            if(moduleFile == null) {
                MessageDialog.openError(Display.getCurrent().getActiveShell(), "Can not locate \"" + moduleName + "\"", "Can not locate \"" + moduleName + "\"");
                return false;
            }else
                return true;
        }else
            return true;
	}
	
	private UnitNodeDiagram load(String moduleName) throws Exception {
        InputStream in;
        File f = new File(moduleName);
        if(f.exists())
            in = new FileInputStream(f);
        else {                
            IFile moduleFile = (IFile)diagram.getFilePath().getParent().findMember(moduleName);
            if(moduleFile == null)
                return null;
            
            in = moduleFile.getContents(false);
        }
        
        UnitNodeDiagramFactory diagramFactory = new UnitNodeDiagramFactory();
        return diagramFactory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in));
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
