package com.xrosstools.xunit.editor.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gef.EditPart;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.editor.io.UnitNodeDiagramFactory;

public class UnitNodeHelper implements UnitConstants {
	private UnitNodeDiagram diagram;
	
	public UnitNodeHelper(UnitNodeDiagram diagram){
		this.diagram = diagram;
	}
	
	private IContainer getResourceRoot() {
        IContainer parent = diagram.getFilePath().getParent();
        IJavaProject i = JavaCore.create(diagram.getFilePath().getProject());
        
        try {
            for(IClasspathEntry cpe: i.getRawClasspath()) {
                if(parent.getFullPath().toOSString().startsWith(cpe.getPath().toOSString())) {
                    while(!parent.getFullPath().equals(cpe.getPath())) {
                        parent = parent.getParent();
                    }
                    return parent;
                }
            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public IType getTypeFromName(String className) {
	    IProject proj = diagram.getFilePath().getProject();
	    try {
            if(proj.hasNature(JavaCore.NATURE_ID))
                return JavaCore.create(proj).findType(className);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
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
	
	public List<String> getWorkSpaceModuleNames(){
	    IContainer resourceRoot = getResourceRoot();

		List<IPath> names = getWorkSpaceModuleNames(resourceRoot);
		
        // Make it relative
        String rootStr = resourceRoot.getFullPath().toPortableString();
        
        List<String> modelList = new ArrayList<String>();
        for(IPath p: names) {
            if(!diagram.getFilePath().getFullPath().equals(p))
                modelList.add(p.toPortableString().replaceFirst(rootStr, ""));
        }
        
		return modelList;
	}
	
    public List<IPath> getWorkSpaceModuleNames(IContainer folder){
        List<IPath> names = new ArrayList<IPath>();
        try {           
            for(IResource res: folder.members()) {
                if(res.getType() == IResource.FOLDER) {
                    names.addAll(getWorkSpaceModuleNames((IContainer)res));
                } else if(res.getType() == IResource.FILE) {
                    String ext = res.getFileExtension();
                    if(ext != null && ext.equals("xunit")) {
                        names.add(res.getFullPath());
                    }
                }
            }
        } catch (CoreException e) {
            e.printStackTrace(System.err);
        }
        return names;
    }
    
	public boolean isFileExist(String moduleName) {
	    if(!isValid(moduleName))
	        return false;
	    
        if(!new File(moduleName).exists()) {
            IFile moduleFile = (IFile)getResourceRoot().findMember(moduleName);
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
            IFile moduleFile = (IFile)getResourceRoot().findMember(moduleName);
            if(moduleFile == null)
                return null;
            
            in = moduleFile.getContents(false);
        }
        
        UnitNodeDiagramFactory diagramFactory = new UnitNodeDiagramFactory();
        UnitNodeDiagram diagram = diagramFactory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in));
        return diagram;
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
