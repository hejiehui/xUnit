package com.xrosstools.xunit.idea.editor;

import java.util.ArrayList;
import java.util.List;

import com.intellij.openapi.project.Project;
import com.xrosstools.xunit.idea.editor.actions.*;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;
import com.xrosstools.xunit.idea.editor.parts.EditPart;

import javax.swing.*;

public class UnitContextMenuProvider  implements UnitActionConstants, UnitConstants {
    public JPopupMenu buildContextMenu(Project project, UnitNodeDiagram diagram, EditPart selected) {
		JPopupMenu menu = new JPopupMenu();

		if(selected instanceof BaseNodePart)
			getNodeActions(project, menu, (BaseNodePart)selected, diagram);
		else
			addPropertiesActions(project, menu, diagram.getProperties());

		return menu;
    }

    private JMenuItem createItem(WorkbenchPartAction action) {
		JMenuItem item = new JMenuItem(action.getText());
		item.addActionListener(action);
		return item;
	}
    
    private void getNodeActions(Project project, JPopupMenu menu, BaseNodePart nodePart, UnitNodeDiagram diagram){
    	UnitNode node = (UnitNode)nodePart.getModel();
    	menu.add(createItem(new AssignClassNameAction(nodePart)));
    	menu.add(createItem(new OpenClassAction(nodePart)));
    	menu.addSeparator();
    	menu.add(createItem(new AssignDefaultAction(node)));

    	addReferenceAction(project, menu, node, diagram);
    	
    	addPropertiesActions(project, menu, node);
    }

    private void addReferenceAction(Project project, JPopupMenu menu, UnitNode node, UnitNodeDiagram diagram) {
        if(!node.isReferenceAllowed())
            return;
        
        menu.addSeparator();

        JPopupMenu moduleSub = new JPopupMenu(ASSIGN_MODULE);
        moduleSub.add(createItem(new AssignModuleAction(project, node)));
        UnitNodeHelper helper = new UnitNodeHelper(diagram);
        List<String> moduleNames = helper.getWorkSpaceModuleNames();
        if(moduleNames.size() > 0) {
            moduleSub.addSeparator();
            for(String name: moduleNames)
                moduleSub.add(createItem(new SelectModuleAction(node, name)));
        }
        menu.add(moduleSub);

        JPopupMenu referSub = new JPopupMenu(ASSIGN_REFERENCE);
    	for(String name: node.getReferenceValues()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		referSub.add(createItem(new AssignReferenceNameAction(node, name)));
    	}
    	menu.add(referSub);
    }
    
    private void addPropertiesActions(Project project, JPopupMenu menu, UnitNodeProperties properties) {
    	menu.addSeparator();
    	menu.add(createItem(new CreatePropertyAction(project, properties)));
        JPopupMenu subRemove = new JPopupMenu(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(createItem(new RemovePropertyAction(properties, name)));
    	}
    	menu.add(subRemove);

        JPopupMenu subRename = new JPopupMenu(RENAME_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRename.add(createItem(new RenamePropertyAction(project, name, properties)));
    	}
    	menu.add(subRename);
    }
    
    private void addPropertiesActions(Project project, JPopupMenu menu, UnitNode node) {
    	UnitNodeProperties properties = node.getProperties();
    	
    	menu.addSeparator();
    	menu.add(createItem(new CreatePropertyAction(project, properties)));
    	addDefinedPropertiesActions(project, menu, node);
        JPopupMenu subRemove = new JPopupMenu(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(createItem(new RemovePropertyAction(properties, name)));
    	}
    	menu.add(subRemove);

        JPopupMenu subRename = new JPopupMenu(RENAME_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRename.add(createItem(new RenamePropertyAction(project, name, properties)));
    	}
    	menu.add(subRename);
    }

    private void addDefinedPropertiesActions(Project project, JPopupMenu menu, UnitNode node) {
    	List<String> propKeys = new ArrayList<>();
    	UnitNodeProperties properties = node.getProperties();
//
//    	if(node.isValid(node.getImplClassName())){
//    		try {
//                UnitNodeDiagramPart undp = (UnitNodeDiagramPart)editor.getRootEditPart().getContents();
//                IType type = undp.getSourceType(node.getImplClassName());
//                if(type != null) {
//                    for(IField f: type.getFields()) {
//                        if(f.getElementName().startsWith(PROPERTY_KEY_PREFIX) &&
//                                (f.getTypeSignature().equals("Ljava.lang.String;") || f.getTypeSignature().equals("QString;")) &&
//                                f.getConstant() != null) {
//                            String name = f.getConstant().toString();
//                            if(type instanceof SourceType && name.startsWith("\"") && name.endsWith("\"")) {
//                                name = name.substring(1);
//                                name = name.substring(0, name.length()-1);
//                            }
//                            propKeys.add(name);
//                        }
//                    }
//                }
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
//    	}
    	
    	for(String key: properties.getNames())
    	    if(!propKeys.contains(key))
    	        propKeys.add(key);
    	
    	if(propKeys.size() ==  0)
    		return;

        JPopupMenu subSetValue = new JPopupMenu(SET_PROPERTY);
    	for(String key: propKeys){
    		subSetValue.add(createItem(new SetPropertyValueAction(project, properties, key)));
    	}
    	menu.add(subSetValue);
    }
}