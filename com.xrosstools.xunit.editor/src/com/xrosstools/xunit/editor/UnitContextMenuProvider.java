package com.xrosstools.xunit.editor;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
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
import com.xrosstools.xunit.editor.actions.UnitActionConstants;
import com.xrosstools.xunit.editor.io.UnitNodeDiagramFactory;
import com.xrosstools.xunit.editor.model.UnitConstants;
import com.xrosstools.xunit.editor.model.UnitNode;
import com.xrosstools.xunit.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.editor.model.UnitNodeHelper;
import com.xrosstools.xunit.editor.model.UnitNodeProperties;
import com.xrosstools.xunit.editor.parts.BaseNodePart;

public class UnitContextMenuProvider  extends ContextMenuProvider implements UnitActionConstants, UnitConstants {
	private UnitDiagramGraphicalEditor editor;
	private UnitNodeDiagramFactory diagramFactory = new UnitNodeDiagramFactory();
	
    public UnitContextMenuProvider(EditPartViewer viewer, UnitDiagramGraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu) {
		UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();
		
		EditPartViewer viewer = this.getViewer();
		List selected = viewer.getSelectedEditParts();
		if(selected.size() == 1 && selected.get(0) instanceof BaseNodePart)
			getNodeActions(menu, (BaseNodePart)selected.get(0));
		else
			addPropertiesActions(menu, diagram.getProperties());
		
    }
    
    private void getNodeActions(IMenuManager menu, BaseNodePart nodePart){
    	UnitNode node = (UnitNode)nodePart.getModel();
    	menu.add(new AssignClassNameAction(editor, nodePart));
    	menu.add(new OpenClassAction(editor, nodePart));
    	menu.add(new Separator());
    	menu.add(new AssignDefaultAction(editor, node));

    	addReferenceAction(menu, node);
    	
    	addPropertiesActions(menu, node.getProperties());
    }

    private void addReferenceAction(IMenuManager menu, UnitNode node) {
        if(!node.isReferenceAllowed())
            return;
        
        menu.add(new Separator());
    	menu.add(new AssignModuleAction(editor, node));
    	
    	MenuManager sub = new MenuManager(ASSIGN_REFERENCE);

    	for(String name: node.getReferenceValues()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		sub.add(new AssignReferenceNameAction(editor, node, name));
    	}
    	menu.add(sub);
    }
    
    private void addPropertiesActions(IMenuManager menu, UnitNodeProperties properties) {
    	menu.add(new Separator());
    	menu.add(new CreatePropertyAction(editor, properties));
    	MenuManager subRemove = new MenuManager(REMOVE_NODE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRemove.add(new RemovePropertyAction(editor, properties, name));
    	}
    	menu.add(subRemove);

    	MenuManager subRename = new MenuManager(RENAME_NODE_PROPERTY);
    	for(String name: properties.getNames()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		subRename.add(new RenamePropertyAction(editor, name, properties));
    	}
    	menu.add(subRename);
    }
}