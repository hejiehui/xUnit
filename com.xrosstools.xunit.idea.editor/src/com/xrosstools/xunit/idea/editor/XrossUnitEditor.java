package com.xrosstools.xunit.idea.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class XrossUnitEditor extends PsiTreeChangeAdapter implements FileEditor, VirtualFileListener {
    private Project project;
    private VirtualFile virtualFile;
    private JComponent panel;

    public XrossUnitEditor(Project project, VirtualFile virtualFile) {
        this.project = project;
        this.virtualFile = virtualFile;
        PsiManager.getInstance(project).addPsiTreeChangeListener(this);
        VirtualFileManager.getInstance().addVirtualFileListener(this);
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

    private void refresh() {
        ((UnitNodeDiagramPanel)panel).contentsChanged();
    }

    @Override
    public void contentsChanged(VirtualFileEvent event) {
        if(event.getFile() == virtualFile)
            refresh();
    }

    @Override
    public void childReplaced(PsiTreeChangeEvent event) {

        PsiElement oldChild = event.getOldChild();
        PsiElement newChild = event.getNewChild();

        if (oldChild instanceof PsiIdentifier && newChild instanceof PsiIdentifier) {
            PsiIdentifier oldMethod = (PsiIdentifier) oldChild;
            PsiIdentifier newMethod = (PsiIdentifier) newChild;

            if (!oldMethod.getText().equals(newMethod.getText())) {
                FileDocumentManager.getInstance().saveAllDocuments();
            }
        }
    }

    public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
        FileDocumentManager.getInstance().saveAllDocuments();
    }

    @Override
    public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
        if (PsiTreeChangeEvent.PROP_FILE_NAME.equals(event.getPropertyName())) {
            PsiElement element = event.getElement();
            if (element instanceof PsiNamedElement && element.isValid()) {
                if(element instanceof PsiJavaFile) {
                    PsiClass[] classes = ((PsiJavaFile)element).getClasses();
                    if(classes.length > 0) {
                        FileDocumentManager.getInstance().saveAllDocuments();
                        refresh();
                    }
                }
            }
        }
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
