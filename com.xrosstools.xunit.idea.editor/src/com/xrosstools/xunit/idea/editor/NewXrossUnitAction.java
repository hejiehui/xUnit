package com.xrosstools.xunit.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;
import com.xrosstools.idea.gef.actions.CodeGenHelper;

public class NewXrossUnitAction  extends DefaultNewModelFileAction {
    private static final String modelTypeName = "Xross Unit Model";
    private static final String templatePath = "/template/emptyTemplate.xunit";
    private static final String newFileName = "new_xunit_file";

    private static final String NAME_TEMPLATE = "!NAME!";
    public NewXrossUnitAction() {
        super(modelTypeName, XunitFileType.EXTENSION, XunitFileType.ICON, newFileName, templatePath);
    }

    public String createTemplateFor(String fileName) {
        StringBuffer template = new StringBuffer(getTemplate());
        CodeGenHelper.replace(template, NAME_TEMPLATE, fileName.replace('_', ' '));
        return template.toString();
    }
}
