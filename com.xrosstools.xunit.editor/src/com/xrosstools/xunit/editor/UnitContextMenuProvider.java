package com.xrosstools.xunit.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xrosstools.xunit.editor.actions.AssignClassNameAction;
import com.xrosstools.xunit.editor.actions.AssignDefaultAction;
import com.xrosstools.xunit.editor.actions.AssignModuleAction;
import com.xrosstools.xunit.editor.actions.AssignReferenceNameAction;
import com.xrosstools.xunit.editor.actions.CreatePropertyAction;
import com.xrosstools.xunit.editor.actions.OpenClassAction;
import com.xrosstools.xunit.editor.actions.RemovePropertyAction;
import com.xrosstools.xunit.editor.actions.RenamePropertyAction;
import com.xrosstools.xunit.editor.actions.SelectModuleAction;
import com.xrosstools.xunit.editor.actions.SetPropertyValueAction;
import com.xrosstools.xunit.editor.actions.UnitActionConstants;
import com.xrosstools.xunit.editor.model.UnitConstants;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.editor.model.UnitNodeHelper;
import com.xrosstools.xunit.editor.model.UnitNodeProperties;
import com.xrosstools.xunit.editor.parts.BaseNodePart;
import com.xrosstools.xunit.editor.parts.UnitNodeDiagramPart;

public class UnitContextMenuProvider  extends ContextMenuProvider implements UnitActionConstants, UnitConstants {
	private UnitDiagramGraphicalEditor editor;
	
    public UnitContextMenuProvider(EditPartViewer viewer, UnitDiagramGraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu) {
        UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();
        
		EditPartViewer viewer = this.getViewer();
		List selected = viewer.getSelectedEditParts();
		if(selected.size() == 1 && selected.get(0) instanceof BaseNodePart)
			getNodeActions(menu, (BaseNodePart)selected.get(0), diagram);
		else
			addPropertiesActions(menu, diagram.getProperties());
		
    }
    
    private void getNodeActions(IMenuManager menu, BaseNodePart nodePart, UnitNodeDiagram diagram){
    	UnitNode node = (UnitNode)nodePart.getModel();
    	menu.add(new AssignClassNameAction(editor, nodePart));
    	menu.add(new OpenClassAction(editor, nodePart));
    	menu.add(new Separator());
    	menu.add(new AssignDefaultAction(editor, node));

    	addReferenceAction(menu, node, diagram);
    	
    	addPropertiesActions(menu, node);
    }

    private void addReferenceAction(IMenuManager menu, UnitNode node, UnitNodeDiagram diagram) {
        if(!node.isReferenceAllowed())
            return;
        
        menu.add(new Separator());
        
        MenuManager moduleSub = new MenuManager(ASSIGN_MODULE);
        moduleSub.add(new AssignModuleAction(editor, node));
        UnitNodeHelper helper = new UnitNodeHelper(diagram);
        List<String> moduleNames = helper.getWorkSpaceModuleNames();
        if(moduleNames.size() > 0) {
            moduleSub.add(new Separator());
            for(String name: moduleNames)
                moduleSub.add(new SelectModuleAction(editor, node, name));
        }
        menu.add(moduleSub);
    	
    	MenuManager referSub = new MenuManager(ASSIGN_REFERENCE);
    	for(String name: node.getReferenceValues()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		referSub.add(new AssignReferenceNameAction(editor, node, name));
    	}
    	menu.add(referSub);
    }
    
    private void addPropertiesActions(IMenuManager menu, UnitNodeProperties properties) {
    	menu.add(new Separator());
    	menu.add(new CreatePropertyAction(editor, properties));
    	MenuManager subRemove = new MenuManager(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(new RemovePropertyAction(editor, properties, name));
    	}
    	menu.add(subRemove);

    	MenuManager subRename = new MenuManager(RENAME_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRename.add(new RenamePropertyAction(editor, name, properties));
    	}
    	menu.add(subRename);
    }
    
    private void addPropertiesActions(IMenuManager menu, UnitNode node) {
    	UnitNodeProperties properties = node.getProperties();
    	
    	menu.add(new Separator());
    	menu.add(new CreatePropertyAction(editor, properties));
    	addDefinedPropertiesActions(menu, node);
    	MenuManager subRemove = new MenuManager(REMOVE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(new RemovePropertyAction(editor, properties, name));
    	}
    	menu.add(subRemove);

    	MenuManager subRename = new MenuManager(RENAME_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRename.add(new RenamePropertyAction(editor, name, properties));
    	}
    	menu.add(subRename);
    }

    private void addDefinedPropertiesActions(IMenuManager menu, UnitNode node) {
    	List<String> propKeys = new ArrayList();
    	UnitNodeProperties properties = node.getProperties();
    	
    	if(node.isValid(node.getImplClassName())){
    		try {
                UnitNodeDiagramPart undp = (UnitNodeDiagramPart)editor.getRootEditPart().getContents();
                IType type = undp.getSourceType(node.getImplClassName());
                if(type != null) {
                    for(IField f: type.getFields()) {
                        if(f.getElementName().startsWith(PROPERTY_KEY_PREFIX) &&
                                (f.getTypeSignature().equals("Ljava.lang.String;") || f.getTypeSignature().equals("QString;")) &&
                                f.getConstant() != null) {
                            String name = f.getConstant().toString();
                            if(type instanceof SourceType && name.startsWith("\"") && name.endsWith("\"")) {
                                name = name.substring(1);
                                name = name.substring(0, name.length()-1);
                            }
                            propKeys.add(name);
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

    	MenuManager subSetValue = new MenuManager(SET_PROPERTY);
    	for(String key: propKeys){
    		subSetValue.add(new SetPropertyValueAction(editor, properties, key));
    	}
    	menu.add(subSetValue);
    }
}