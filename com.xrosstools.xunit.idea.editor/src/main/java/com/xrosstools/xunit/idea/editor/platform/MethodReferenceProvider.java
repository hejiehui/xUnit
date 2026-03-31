package com.xrosstools.xunit.idea.editor.platform;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.XunitConstants;
import org.jetbrains.annotations.NotNull;

public class MethodReferenceProvider extends PsiReferenceProvider implements XunitConstants {
    private PsiReference[] EMPTY = PsiReference.EMPTY_ARRAY;
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        String text = ((XmlAttributeValue)element).getValue();

        String methodName = UnitNode.getMethodName(text);
        String className = UnitNode.getClassNamePart(text);

        //We only support rename non default method
        if(DEFAULT_METHOD.equals(methodName) || methodName == null || methodName.trim().length() == 0) {
            return PsiReference.EMPTY_ARRAY;
        }

        int start = className.length() + SEPARATOR.length();
        TextRange property = new TextRange(start, start + methodName.length());
        PsiReference methodRef = new MethodReference(element, className, methodName, property);

        return new PsiReference[]{methodRef};
    }
}
