package com.xrosstools.xunit.idea.editor.platform;

import com.intellij.patterns.XmlAttributeValuePattern;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.xrosstools.xunit.idea.editor.XunitFileType;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.XunitConstants;

import static com.intellij.patterns.StandardPatterns.string;
import static com.intellij.patterns.XmlPatterns.xmlFile;

public class XunitReferenceContributor extends PsiReferenceContributor implements XunitConstants {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        for(BehaviorType type: BehaviorType.values())
            register(registrar, type);
    }

    private void register(PsiReferenceRegistrar registrar, BehaviorType type) {
        XmlAttributeValuePattern pattern = XmlPatterns.xmlAttributeValue()
                .withParent(
                        XmlPatterns.xmlAttribute().withName(CLASS)
                                .withParent(XmlPatterns.xmlTag().withName(type.name())))
                .inFile(xmlFile().withName(string().endsWith("." + XunitFileType.EXTENSION)));

        registrar.registerReferenceProvider(pattern, new ClassReferenceProvider());
        registrar.registerReferenceProvider(pattern, new MethodReferenceProvider());
    }
}
