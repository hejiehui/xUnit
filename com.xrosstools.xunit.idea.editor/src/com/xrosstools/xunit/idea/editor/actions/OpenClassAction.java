package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;
import com.intellij.util.xml.ui.UndoHelper;
import com.xrosstools.xunit.idea.editor.XrossUnitEditor;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

public class OpenClassAction extends WorkbenchPartAction implements UnitActionConstants {
	private Project project;
    private UnitNode node;
	private String className;
	public OpenClassAction(Project project, BaseNodePart nodePart) {
		setText(OPEN_CLASS);
		this.project = project;
        node = (UnitNode)nodePart.getModel();
        className = node.getImplClassName();
	}

	public Command createCommand() {
		openClass(project, className);
		return null;
	}

    public static void openClassOrReference(Project project, UnitNode node) {
	    if(node.isValid(node.getClassName()))
            openClass(project, node.getImplClassName());
	    else
	        openReference(project, node.getHelper().findResourcesRoot().findFileByRelativePath(node.getModuleName()), node.getReferenceName());
    }

	public static void openClass(Project project, String className){
		GlobalSearchScope scope = GlobalSearchScope.allScope (project);

		//VirtualFileManager.getInstance().findFileByUrl("jar://path/to/file.jar!/path/to/file.class");

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        if (null == psiClass) {
            Messages.showErrorDialog("Can not open " + className, "Error");
        }else
            OpenSourceUtil.navigate(psiClass);
	}

	public static void openReference(Project project, VirtualFile module, String name) {
        if (null == module){
            Messages.showErrorDialog("Can not open " + module, "Error");
        }else {
            FileEditor[] editors = FileEditorManager.getInstance(project).openFile(module, true);
            if(editors != null && editors.length == 1 && editors[0] instanceof XrossUnitEditor) {
                XrossUnitEditor editor = (XrossUnitEditor) editors[0];
                editor.select(name);
            }
        }
	}
}
