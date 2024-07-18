package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.xrosstools.xunit.idea.editor.commands.ChangeMethodCommand;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.PrimaryNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChangeMethodAction extends WorkbenchPartAction implements UnitActionConstants {
    private UnitNode node;
    private String methodName;
    public ChangeMethodAction(UnitNode node, String methodName, boolean isPrivate) {
        setText(isPrivate ? '-' + methodName : methodName);
        this.node = node;
        this.methodName = methodName;
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
