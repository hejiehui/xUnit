package com.xrosstools.xunit.idea.editor;

import com.intellij.ide.highlighter.XmlLikeFileType;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class XunitFileType extends XmlLikeFileType {
    public static final String NAME = "Xross Unit Model File";
    public static final String DESCRIPTION = "Xross Unit Model File";
    public static final String EXTENSION = "xunit";
    public static final Icon ICON = XrossUnitIcons.Chain;

    public static final XunitFileType INSTANCE = new XunitFileType();

    protected XunitFileType() {
        super(XMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return ICON;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }
}
