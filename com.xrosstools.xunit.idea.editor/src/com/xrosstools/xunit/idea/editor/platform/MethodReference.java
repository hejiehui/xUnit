package com.xrosstools.xunit.idea.editor.platform;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.IncorrectOperationException;
import com.xrosstools.xunit.idea.editor.model.XunitConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MethodReference extends PsiReferenceBase implements PsiPolyVariantReference, XunitConstants {
    private String className;
    private String methodName;

    public MethodReference(PsiElement element, String className, String methodName, TextRange range) {
        super(element, range);
        this.className = className;
        this.methodName = methodName;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        Project project = getElement().getProject();
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
        if(psiClass == null) return null;

        return psiClass.findMethodsByName(methodName, false)[0];
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        PsiElement resolved = resolve();
        if (resolved != null) {
            return new ResolveResult[]{new PsiElementResolveResult(resolved)};
        }
        return ResolveResult.EMPTY_ARRAY;
    }

    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        XmlAttributeValue value = (XmlAttributeValue)getElement();
        XmlAttribute attribute = XmlElementFactory.getInstance(getElement().getProject()).createAttribute(CLASS, className + SEPARATOR + newElementName, value.getParent());

        value.getParent().replace(attribute);
        return attribute;
    }

    public Object[] getVariants() {
        return new Object[0];
    }
}
