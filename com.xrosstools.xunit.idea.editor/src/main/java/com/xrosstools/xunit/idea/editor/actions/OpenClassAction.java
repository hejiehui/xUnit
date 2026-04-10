package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;
import com.xrosstools.idea.gef.DiagramEditor;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.XunitConstants;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

import java.awt.event.ActionEvent;

public class OpenClassAction extends Action implements UnitActionConstants, XunitConstants {
	private Project project;
    private UnitNode node;
	public OpenClassAction(Project project, BaseNodePart nodePart) {
		setText(OPEN_CLASS);
		this.project = project;
        node = (UnitNode)nodePart.getModel();
	}

    public void actionPerformed(ActionEvent e) {
        //Because unit node has "default" implementation, so we can not just getClassName()
        openClass(project, node.getImplClassName(), node.getMethodName());
    }

    @Override
    public Command createCommand() {
        return null;
    }

    public static void openClassOrReference(Project project, UnitNode node) {
	    if(node.isValid(node.getClassName()))
            openClass(project, node.getImplClassName(), node.getMethodName());
	    else
	        openReference(project, node.getHelper().findResourcesRoot().findFileByRelativePath(node.getModuleName()), node.getReferenceName());
    }

	public static void openClass(Project project, String classImpName, String methodName){
        String implementation = ImplementationUtil.replaceMethodName(classImpName, methodName);
        ImplementationUtil.openImpl(project, implementation);
	}

	public static void openReference(Project project, VirtualFile module, String name) {
        if (null == module){
            Messages.showErrorDialog("Can not open " + module, "Error");
        }else {
            FileEditor[] editors = FileEditorManager.getInstance(project).openFile(module, true);
            if(editors.length == 1 && editors[0] instanceof DiagramEditor) {
                DiagramEditor<?> editor = (DiagramEditor<?>) editors[0];
                editor.selectTopLevelElement(UnitConstants.PROP_NAME, name);
            }
        }
	}
}
