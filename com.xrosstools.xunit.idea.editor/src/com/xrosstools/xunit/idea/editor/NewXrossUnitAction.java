package com.xrosstools.xunit.idea.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.util.OpenSourceUtil;
import com.xrosstools.xunit.idea.editor.util.XmlHelper;

import java.io.IOException;

public class NewXrossUnitAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final Project project = anActionEvent.getProject();
        VirtualFile selected = anActionEvent.getData(CommonDataKeys.VIRTUAL_FILE);
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
                return true;
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
                    VirtualFile newFile = dir.createChildData(project, name + ".xunit");
                    newFile.setBinaryContent("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xunit description=\"\" name=\"\" packageId=\"\"><configure><category name=\"default\"/></configure><units/></xunit>".getBytes());
                } catch (Throwable e) {
                    throw new IllegalStateException("Can not save document " + name, e);
                }
            }
        });
    }
}
