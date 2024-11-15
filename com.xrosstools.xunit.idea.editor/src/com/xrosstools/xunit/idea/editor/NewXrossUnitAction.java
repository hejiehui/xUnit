package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.xunit.idea.editor.actions.GenerateFactoryAction;

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

        Messages.InputDialog dialog = new Messages.InputDialog(project, "New Xross Unit Model", "Name: ", XunitFileType.ICON, "new_xunit_file", new InputValidator() {
            @Override
            public boolean checkInput(String s) {
                return true;
            }

            @Override
            public boolean canClose(String s) {
                if(s== null || s.trim().length() == 0)
                    return false;

                s = s.trim();
                String name = s + "." + XunitFileType.EXTENSION;
                for(VirtualFile c: dir.getChildren()) {
                    if (c.getName().equalsIgnoreCase(name)) {
                        Messages.showErrorDialog("Name \"" + name + "\" is already used, please chose another one.", "Error");
                        return false;
                    }
                }
                return true;
            }
        });
        dialog.show();

        if (dialog.getExitCode() != 0) {
            return;
        }

        final String name = dialog.getInputString();

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                try {
                    VirtualFile newFile = dir.createChildData(project, name + "." + XunitFileType.EXTENSION);
                    StringBuffer sbf = GenerateFactoryAction.getTemplate("/template/emptyTemplate.xunit");
                    GenerateFactoryAction.replace(sbf, "!NAME!", name.replace('_', ' '));

                    newFile.setBinaryContent(sbf.toString().getBytes());
                    FileEditorManager.getInstance(project).openFile(newFile, true);
                } catch (Throwable e) {
                    throw new IllegalStateException("Can not save document " + name, e);
                }
            }
        });
    }
}
