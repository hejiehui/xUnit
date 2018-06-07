package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;
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
        className = node.getClassName();
	}

	public Command createCommand() {
		openClass(project, className);
		return null;
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
}
