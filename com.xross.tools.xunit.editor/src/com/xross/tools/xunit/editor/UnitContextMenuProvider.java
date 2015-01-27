package com.xross.tools.xunit.editor;

import java.util.List;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import com.xross.tools.xunit.editor.actions.AssignClassNameAction;
import com.xross.tools.xunit.editor.actions.AssignDefaultAction;
import com.xross.tools.xunit.editor.actions.AssignReferenceNameAction;
import com.xross.tools.xunit.editor.actions.CreateCategoryAction;
import com.xross.tools.xunit.editor.actions.CreateEntryAction;
import com.xross.tools.xunit.editor.actions.CreateNodePropertyAction;
import com.xross.tools.xunit.editor.actions.OpenClassAction;
import com.xross.tools.xunit.editor.actions.RemoveCategoryAction;
import com.xross.tools.xunit.editor.actions.RemoveEntryAction;
import com.xross.tools.xunit.editor.actions.RenameCategoryAction;
import com.xross.tools.xunit.editor.actions.RenameEntryAction;
import com.xross.tools.xunit.editor.actions.UnitActionConstants;
import com.xross.tools.xunit.editor.model.UnitConfigure;
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;
import com.xross.tools.xunit.editor.parts.BaseNodePart;

public class UnitContextMenuProvider  extends ContextMenuProvider implements UnitActionConstants, UnitConstants {
	private UnitDiagramGraphicalEditor editor;
	private UnitConfigure configure;
    public UnitContextMenuProvider(EditPartViewer viewer, UnitDiagramGraphicalEditor editor) {
        super(viewer);
        this.editor = editor;
    }
    
    public void buildContextMenu(IMenuManager menu) {
		UnitNodeDiagram diagram = (UnitNodeDiagram)editor.getRootEditPart().getContents().getModel();
		configure = diagram.getConfigure();
		
		EditPartViewer viewer = this.getViewer();
		List selected = viewer.getSelectedEditParts();
		if(selected.size() == 1 && selected.get(0) instanceof BaseNodePart)
			getNodeActions(menu, (BaseNodePart)selected.get(0));
		else if(configure.isSimple())
			getSimpleActions(menu);
		else
			getCategoryActions(menu);
		
    }
    
    private void getNodeActions(IMenuManager menu, BaseNodePart nodePart){
    	UnitNode node = (UnitNode)nodePart.getModel();
    	menu.add(new AssignClassNameAction(editor, nodePart));
    	menu.add(new OpenClassAction(editor, nodePart));
    	menu.add(new Separator());
    	menu.add(new AssignDefaultAction(editor, node));
    	menu.add(new Separator());
    	MenuManager sub = new MenuManager(ASSIGN_REFERENCE);
    	for(String name: node.getReferenceValues()){
    		if(EMPTY_VALUE.equals(name))
    			continue;
    		sub.add(new AssignReferenceNameAction(editor, node, name));
    	}
    	menu.add(sub);
    	menu.add(new Separator());
    	menu.add(new CreateNodePropertyAction(editor, node.getProperties()));
    }
    
    private void getSimpleActions(IMenuManager menu){
    	WorkbenchPartAction action;
    	MenuManager entrySub;
    	
    	action = new CreateEntryAction(editor, DEFAULT_CATEGORY, configure);
    	action.setText(CREATE_PROPERTY);
    	menu.add(action);

    	entrySub = new MenuManager(RENAME_PROPERTY);
 		for(String key: configure.getEntryNames(DEFAULT_CATEGORY))
 			entrySub.add(new RenameEntryAction(editor, DEFAULT_CATEGORY, key, configure));
 		menu.add(entrySub);

 		entrySub = new MenuManager(REMOVE_PROPERTY);
 		for(String key: configure.getEntryNames(DEFAULT_CATEGORY))
 			entrySub.add(new RemoveEntryAction(editor, DEFAULT_CATEGORY, key));
 		menu.add(entrySub);
 		
     	menu.add(new Separator());
     	menu.add(new CreateCategoryAction(editor, configure));
    }
    
    private void getCategoryActions(IMenuManager menu){
     	getCreateEntryActions(menu);
     	menu.add(new Separator());
     	getRenameEntryActions(menu);
     	menu.add(new Separator());
     	getRemoveEntryActions(menu);
     	menu.add(new Separator());
     	menu.add(new CreateCategoryAction(editor, configure));
     	getRenameCategoryActions(menu);
     	getRemoveCategoryActions(menu);
    }
    
    private void getRenameCategoryActions(IMenuManager menu){
    	MenuManager sub = new MenuManager(RENAME_CATEGORY);
     	for(String catName: configure.getCategoryNames())
     		sub.add(new RenameCategoryAction(editor, catName, configure));
     	menu.add(sub);
    }

    private void getRemoveCategoryActions(IMenuManager menu){
    	MenuManager sub = new MenuManager(REMOVE_CATEGORY);
     	for(String catName: configure.getCategoryNames())
     		sub.add(new RemoveCategoryAction(editor, catName));
     	menu.add(sub);
    }

    private void getCreateEntryActions(IMenuManager menu){
     	for(String catName: configure.getCategoryNames())
     		menu.add(new CreateEntryAction(editor, catName, configure));
    }
    
    private void getRenameEntryActions(IMenuManager menu){
     	for(String catName: configure.getCategoryNames()){
     		MenuManager entrySub = new MenuManager(RENAME_PROPERTY + " in " + catName);
     		for(String key: configure.getEntryNames(catName))
     			entrySub.add(new RenameEntryAction(editor, catName, key, configure));
     		menu.add(entrySub);
     	}
    }

    private void getRemoveEntryActions(IMenuManager menu){
     	for(String catName: configure.getCategoryNames()){
     		MenuManager entrySub = new MenuManager(REMOVE_PROPERTY + " from " + catName);
     		for(String key: configure.getEntryNames(catName))
     			entrySub.add(new RemoveEntryAction(editor, catName, key));
     		menu.add(entrySub);
     	}
    }
}