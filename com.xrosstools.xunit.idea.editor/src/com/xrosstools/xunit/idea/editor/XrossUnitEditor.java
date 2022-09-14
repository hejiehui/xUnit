package com.xrosstools.xunit.idea.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.xunit.idea.editor.io.UnitNodeDiagramFactory;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;
import com.xrosstools.xunit.idea.editor.parts.EditContext;
import com.xrosstools.xunit.idea.editor.parts.EditPart;
import com.xrosstools.xunit.idea.editor.parts.UnitNodePartFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.PropertyChangeListener;

public class XrossUnitEditor implements FileEditor, FileEditorManagerListener {
    private Project project;
    private VirtualFile virtualFile;
    private JComponent panel;

    public XrossUnitEditor(Project project, VirtualFile virtualFile) {
        this.project = project;
        this.virtualFile = virtualFile;
    }

    public void select(String name) {
        ((UnitNodeDiagramPanel)panel).selectUnit(name);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        if(panel != null)
            return panel;

        try{
            panel = new UnitNodeDiagramPanel(project, virtualFile);
        }catch(Throwable e) {
            panel = new JLabel("Failed to load Xross Unit Model File: " + e.toString());
        }

        return panel;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return panel;
    }

    @NotNull
    @Override
    public String getName() {
        return "Xunit designer";
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {
        panel.repaint();
    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }


    @NotNull
    @Override
    public VirtualFile getFile() {
        return virtualFile;
    }
}
