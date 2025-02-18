package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.TransactionGuard;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDocumentManager;
import org.jetbrains.annotations.NotNull;

public class XrossUnitEditorProvider implements FileEditorProvider, DumbAware {
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile.getFileType() == XunitFileType.INSTANCE || XunitFileType.EXTENSION.equalsIgnoreCase(virtualFile.getExtension());
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        ModalityState modalityState = ModalityState.defaultModalityState();

        TransactionGuard.getInstance().submitTransaction(project, () ->
            ApplicationManager.getApplication().invokeAndWait(() ->
                FileDocumentManager.getInstance().saveAllDocuments()
            , modalityState)
        );

        return new XrossUnitEditor(project, virtualFile);
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "Xross Unit Model Editor";
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
