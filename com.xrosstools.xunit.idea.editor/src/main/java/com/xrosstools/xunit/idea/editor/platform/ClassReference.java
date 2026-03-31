package com.xrosstools.xunit.idea.editor.platform;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

public class ClassReference extends PsiReferenceBase implements PsiReference {
    private String className;
    public ClassReference(PsiElement element, String className, TextRange range) {
        super(element, range);
        this.className = className;
    }

    @NotNull
    @Override
    public PsiElement resolve() {
        PsiElement element = getElement();
        return element;
//        Project project = getElement().getProject();
//        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
//
//        if (psiClass == null)
//            return null;
//
//        return psiClass;
    }

    public Object[] getVariants() {
        return new Object[0];
    }
}
