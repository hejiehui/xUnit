package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.OpenSourceUtil;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

public class OpenClassAction extends WorkbenchPartAction implements UnitActionConstants {
	private BaseNodePart nodePart;
	private Project project;
    private UnitNode node;
	private String className;
	public OpenClassAction(Project project, BaseNodePart nodePart) {
		setText(OPEN_CLASS);
		this.nodePart = nodePart;
		this.project = project;
        node = (UnitNode)nodePart.getModel();
        className = node.getClassName();
	}

	protected boolean calculateEnabled() {
		return node.isValid(className);
	}

	public void run() {
		openClass();
	}

	public void openClass(){
		GlobalSearchScope scope = GlobalSearchScope.allScope (project);

		//VirtualFileManager.getInstance().findFileByUrl("jar://path/to/file.jar!/path/to/file.class");

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        if (null == psiClass) {
            Messages.showErrorDialog("Can not open " + className, "Error");
        }else
            OpenSourceUtil.navigate(psiClass);
	}
}
