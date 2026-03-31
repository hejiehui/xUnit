package com.xrosstools.xunit.idea.editor.platform;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.XunitConstants;
import org.jetbrains.annotations.NotNull;

public class ClassReferenceProvider extends PsiReferenceProvider implements XunitConstants {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        String text = ((XmlAttributeValue)element).getValue();

        String className = UnitNode.getClassNamePart(text);

        TextRange property = new TextRange(element.getStartOffsetInParent(), element.getStartOffsetInParent() + className.length());
        PsiReference classRef = new ClassReference(element, className, property);

        return new PsiReference[]{classRef};
    }
}
