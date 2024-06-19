package com.xrosstools.xunit.idea.editor;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.xrosstools.xunit.idea.editor.actions.*;
import com.xrosstools.xunit.idea.editor.model.*;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;
import com.xrosstools.xunit.idea.editor.parts.EditPart;

import javax.swing.*;

public class UnitContextMenuProvider  implements UnitActionConstants, UnitConstants {
    private PropertyChangeListener listener;
    public UnitContextMenuProvider(PropertyChangeListener listener) {
        this.listener = listener;
    }

    public JPopupMenu buildContextMenu(Project project, UnitNodeDiagram diagram, EditPart selected) {
		JPopupMenu menu = new JPopupMenu();

		if(selected instanceof BaseNodePart)
			getNodeActions(project, menu, (BaseNodePart)selected, diagram);
		else
			addPropertiesActions(project, menu, diagram.getProperties());

		return menu;
    }

    private JMenuItem createItem(WorkbenchPartAction action) {
    	action.setListener(listener);
		JMenuItem item = new JMenuItem(action.getText());
		item.addActionListener(action);
		return item;
	}
    
    private void getNodeActions(Project project, JPopupMenu menu, BaseNodePart nodePart, UnitNodeDiagram diagram){
    	UnitNode node = (UnitNode)nodePart.getModel();
    	menu.add(createItem(new AssignClassNameAction(project, nodePart)));
    	menu.add(createItem(new OpenClassAction(project, nodePart)));
		addMethodAction(project, menu, node);
    	menu.addSeparator();
    	menu.add(createItem(new AssignDefaultAction(node)));

    	addReferenceAction(project, menu, node, diagram);
    	
    	addPropertiesActions(project, menu, node);
    }

	private void addMethodAction(Project project, JPopupMenu menu, UnitNode node) {
    	if(!ChangeMethodAction.isMethodSupported(node) || MSG_DEFAULT.equals(node.getClassName()))
    		return;

		JMenu methodMenu = new JMenu(REFERENCE_METHOD_MSG + node.getMethodName());
		methodMenu.add(createItem(new ChangeMethodAction(node, XunitConstants.DEFAULT_METHOD)));
		for(String m: ChangeMethodAction.getMethods(project, node.getClassNamePart())) {
			methodMenu .add(createItem(new ChangeMethodAction(node, m)));
		}
		menu.add(methodMenu);
	}

    private void addReferenceAction(Project project, JPopupMenu menu, UnitNode node, UnitNodeDiagram diagram) {
        if(!node.isReferenceAllowed())
            return;
        
        menu.addSeparator();

        JMenu moduleSub = new JMenu(ASSIGN_MODULE);
        moduleSub.add(createItem(new AssignModuleAction(project, node)));
        UnitNodeHelper helper = new UnitNodeHelper(diagram);
        List<String> moduleNames = helper.getWorkSpaceModuleNames();
        if(moduleNames.size() > 0) {
            moduleSub.addSeparator();
            for(String name: moduleNames)
                moduleSub.add(createItem(new SelectModuleAction(node, name)));
        }
        menu.add(moduleSub);

        JMenu referSub = new JMenu(ASSIGN_REFERENCE);
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
        JMenu subRemove = new JMenu(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(createItem(new RemovePropertyAction(properties, name)));
    	}
    	menu.add(subRemove);

        JMenu subRename = new JMenu(RENAME_PROPERTY);
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
        JMenu subRemove = new JMenu(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(createItem(new RemovePropertyAction(properties, name)));
    	}
		menu.add(subRemove);

        JMenu subRename = new JMenu(RENAME_PROPERTY);
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

    	if(node.isValid(node.getImplClassName())){
    		try {
                GlobalSearchScope scope = GlobalSearchScope.allScope (project);

                //VirtualFileManager.getInstance().findFileByUrl("jar://path/to/file.jar!/path/to/file.class");

                PsiClass type = JavaPsiFacade.getInstance(project).findClass(node.getImplClassName(), scope);

                if (null != type) {
                    for (PsiField f : type.getFields()) {
                        if (f.getNameIdentifier().getText().startsWith(PROPERTY_KEY_PREFIX) && f.getType().getPresentableText().equals("String")) {
                            String text = f.getText();
                            int start =text.indexOf('"');
                            if(start <=0)
                                continue;

                            int end = text.indexOf('"', start + 1);
                            propKeys.add(text.substring(start +1, end));
                        }
                    }
                }
			} catch (Throwable e) {
				e.printStackTrace();
			}
    	}
    	
    	for(String key: properties.getNames())
    	    if(!propKeys.contains(key))
    	        propKeys.add(key);
    	
    	if(propKeys.size() ==  0)
    		return;

        JMenu subSetValue = new JMenu(SET_PROPERTY);
    	for(String key: propKeys){
    		subSetValue.add(createItem(new SetPropertyValueAction(project, properties, key)));
    	}
    	menu.add(subSetValue);
    }
}