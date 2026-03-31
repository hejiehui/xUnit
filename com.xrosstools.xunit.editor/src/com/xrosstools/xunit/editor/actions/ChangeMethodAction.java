package com.xrosstools.xunit.editor.actions;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.ui.IWorkbenchPart;

import com.xrosstools.xunit.editor.commands.ChangeMethodCommand;
import com.xrosstools.xunit.editor.model.BehaviorType;
import com.xrosstools.xunit.editor.model.PrimaryNode;
import com.xrosstools.xunit.editor.model.UnitNode;

public class ChangeMethodAction extends WorkbenchPartAction implements UnitActionConstants {
    private UnitNode node;
    private String methodName;
    public ChangeMethodAction(IWorkbenchPart part, UnitNode node, String methodName, boolean isPrivate) {
    	super(part);
        setText(isPrivate ? '-' + methodName : methodName);
        this.node = node;
        this.methodName = methodName;
    }

	protected boolean calculateEnabled() {
		return true;
	}

    public Command createCommand() {
        return new ChangeMethodCommand(node, methodName );
    }

    public static boolean isMethodSupported(UnitNode node) {
        return node instanceof PrimaryNode && node.getType() != BehaviorType.dispatcher;
    }

    public static List<PsiMethod> getMethods(Project project, String currentImpl) {
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(currentImpl, scope);

        if (psiClass == null)
            return Collections.emptyList();

        List<PsiMethod> methods = new ArrayList<>();
        for (PsiMethod m : psiClass.getMethods()) {
            if (m.isConstructor()) continue;

            methods.add(m);
        }

        return methods;
    }
}
