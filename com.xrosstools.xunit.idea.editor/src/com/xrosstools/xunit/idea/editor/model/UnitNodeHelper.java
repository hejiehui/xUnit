package com.xrosstools.xunit.idea.editor.model;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.xrosstools.xunit.idea.editor.XunitFileType;
import com.xrosstools.xunit.idea.editor.io.UnitNodeDiagramFactory;
import com.xrosstools.xunit.idea.editor.parts.EditPart;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnitNodeHelper implements UnitConstants {
    private UnitNodeDiagram diagram;
    public UnitNodeHelper(UnitNodeDiagram diagram){
        this.diagram = diagram;
    }

    public String[] getReferenceNames(UnitNode node, EditPart curPart){
        if(!node.isValid(node.getModuleName()) || node.getModuleName().equals(diagram.getFilePath().getName())) {
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

    public VirtualFile findResourcesRoot() {
        String modelPath = diagram.getFilePath().getPath();
        for (Module m : ModuleManager.getInstance(diagram.getProject()).getModules()) {
            for (VirtualFile resourceDirectory : ModuleRootManager.getInstance(m).getSourceRoots(JavaModuleSourceRootTypes.RESOURCES)) {
                if (modelPath.contains(resourceDirectory.getPath())) {
                    return resourceDirectory;
                }
            }
        }
        return diagram.getFilePath().getParent();
    }

    public List<String> getWorkSpaceModuleNames(){
        VirtualFile root = findResourcesRoot();
        List<VirtualFile> names = getWorkSpaceModuleNames(root);

        List<String> displayNames = new ArrayList<>();
        for(VirtualFile res: names) {
            if(!res.equals(diagram.getFilePath())) {
                displayNames.add(res.getPath().replaceFirst(root.getPath() + '/', ""));
            }
        }
        return displayNames;
    }

    public List<VirtualFile> getWorkSpaceModuleNames(VirtualFile root){
        List<VirtualFile> names = new ArrayList<>();
        try {
            for(VirtualFile res: root.getChildren()) {
                if(res.isDirectory()){
                    names.addAll(getWorkSpaceModuleNames(res));
                } else {
                    if(XunitFileType.EXTENSION.equalsIgnoreCase(res.getExtension())) {
                        names.add(res);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return names;
    }

    public boolean isFileExist(String moduleName) {
        if(findByName(moduleName) != null)
            return true;

        Messages.showInfoMessage("Can not locate \"" + moduleName + "\"", "Can not locate \"" + moduleName + "\"");
        return false;
    }

    private VirtualFile findByName(String moduleName) {
        if (!isValid(moduleName))
            return null;

        File f = new File(moduleName);
        if (new File(moduleName).exists())
            return LocalFileSystem.getInstance().findFileByIoFile(f);

        VirtualFile moduleFile = diagram.getFilePath().getParent().findChild(moduleName);
        if (moduleFile != null)
            return moduleFile;

        VirtualFile moduleContentRoot = ProjectRootManager.getInstance(diagram.getProject()).getFileIndex().getSourceRootForFile(diagram.getFilePath());
        return VfsUtilCore.findRelativeFile(moduleName, moduleContentRoot);
    }

    private UnitNodeDiagram load(String moduleName) throws Exception {
        VirtualFile moduleFile = findByName(moduleName);
        if(moduleFile == null)
            return null;

        UnitNodeDiagramFactory diagramFactory = new UnitNodeDiagramFactory();
        return diagramFactory.getFromDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(moduleFile.getInputStream()));
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
        if(diagram != null) {
            for (UnitNode unit : diagram.getUnits()) {
                String name = unit.getName();
                if (unit.getType() != type || !isValid(name))
                    continue;
                if (!name.equals(excluded))
                    names.add(unit.getName());
            }
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
