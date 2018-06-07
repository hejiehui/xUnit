package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.util.OpenSourceUtil;
import com.xrosstools.xunit.idea.editor.util.XmlHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewXrossUnitAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getProject();
        VirtualFile selected = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
        if(selected == null) {
            Messages.showErrorDialog("Please select location first", "Error");
            return;
        }

        if(!selected.isDirectory()) {
            selected = selected.getParent();
        }

        final VirtualFile dir = selected;

        Messages.InputDialog dialog = new Messages.InputDialog(project, "New Xross Unit Model", "Name: ", IconLoader.findIcon(Activator.getIconPath("chain")), "new_xunit_file", new InputValidator() {
            @Override
            public boolean checkInput(String s) {
                return true;
            }

            @Override
            public boolean canClose(String s) {
                if(s!= null || s.trim().length() == 0)
                    return false;

                String name = s + "." + XunitFileType.EXTENSION;
                for(VirtualFile c: dir.getChildren()) {
                    if (c.getName().equalsIgnoreCase(name)) {
                        Messages.showErrorDialog("Name \"" + name + "\" is already used, please chose another one.", "Error");
                        return false;
                    }
                }
                return s!= null ;
            }
        });
        dialog.show();

        if (dialog.getExitCode() != 0)
            return;

        final String name = dialog.getInputString();

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                try {
                    VirtualFile newFile = dir.createChildData(project, name + "." + XunitFileType.EXTENSION);

                    BufferedInputStream in = new BufferedInputStream(getClass().getResourceAsStream("/template/emptyTemplate.xunit"));
                    int buf_size = 1024;
                    byte[] buffer = new byte[buf_size];
                    int len;
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    while (-1 != (len = in.read(buffer, 0, buf_size))) {
                        bos.write(buffer, 0, len);
                    }

                    newFile.setBinaryContent(bos.toByteArray());
                    FileEditorManager.getInstance(project).openFile(newFile, true);
                } catch (Throwable e) {
                    throw new IllegalStateException("Can not save document " + name, e);
                }
            }
        });
    }
}
