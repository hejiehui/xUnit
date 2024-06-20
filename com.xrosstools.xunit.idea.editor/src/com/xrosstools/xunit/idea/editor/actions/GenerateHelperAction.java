package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;

public class GenerateHelperAction extends WorkbenchPartAction {
    private static final String UNIT_COMMENTS =
            "    //%s\n";

    private static final String PROCESSOR_DEF =
            "    public static ProcessorCreator %s = processor(\"%s\");\n\n"; //xunit id

    private static final String CONVERTER_DEF =
            "    public static ConverterCreator %s = converter(\"%s\");\n\n"; //xunit id

    private static final String PROCESSOR_FACTORY =
            "    private static ProcessorCreator processor(String id) {\n" +
            "        return load().processorCreator(id);\n"+
            "    }\n";

    private static final String CONVERTER_FACTORY =
            "    private static ConverterCreator converter(String id) {\n" +
            "        return load().converterCreator(id);\n"+
            "    }\n";

    private Project project;
    private VirtualFile file;
    private UnitNodeDiagram diagram;

    public GenerateHelperAction(Project project, VirtualFile file, UnitNodeDiagram diagram){
        this.project = project;
        this.file = file;
        this.diagram = diagram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer codeBuf = getTemplate("/template/HelperTemplate.txt");
        replace(codeBuf, "!PACKAGE!", getValue(diagram.getPackageId()));
        replace(codeBuf, "!DESCRIPTION!", getValue(diagram.getDescription()));
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", toClassName(diagram.getName()));
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath());

        replace(codeBuf, "!XUNIT_DEFINITIONS!", generateBody());

        new CodeDisplayer("Generated helper", codeBuf.toString()).show();
    }

    private String generateBody() {
        StringBuffer constants = new StringBuffer();

        boolean hasProcessor = false;
        boolean hasConverter = false;
        for(UnitNode unit: diagram.getUnits()) {
            if(unit.getDescription() != null && unit.getDescription().trim().length() > 0)
                constants.append(String.format(UNIT_COMMENTS, unit.getDescription()));

            if(unit.getType() == BehaviorType.processor)
                hasProcessor = true;

            if(unit.getType() == BehaviorType.converter)
                hasConverter = true;

            String template = unit.getType() == BehaviorType.processor ? PROCESSOR_DEF : CONVERTER_DEF;
            String id = unit.getName();
            String constDef = id.contains(" ") ? id.replace(' ', '_') : id;

            constants.append(String.format(template, constDef, id));
        }

        if(hasProcessor)
            constants.append(PROCESSOR_FACTORY);

        if(hasConverter)
            constants.append(CONVERTER_FACTORY);

        return constants.toString();
    }

    public Command createCommand() {
        return null;
    }

    // TODO This is copied from decision tree. and should be move to GEF.
    public static StringBuffer getTemplate(String filePath){
        StringBuffer codeBuf = new StringBuffer();

        BufferedReader reader = new BufferedReader(new InputStreamReader(GenerateHelperAction.class.getResourceAsStream(filePath)));
        String line;
        try {
            while((line = reader.readLine()) != null)
                codeBuf.append(line).append('\n');
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return codeBuf;
    }

    public static void replace(StringBuffer codeBuf, String replacementMark, String replacement){
        int start;
        while((start = codeBuf.indexOf(replacementMark)) >= 0) {
            codeBuf.replace(start, start + replacementMark.length(), replacement);
        }
    }

    public static String toClassName(String label) {
        StringBuffer clazz = new StringBuffer();
        for(String s: label.split(" ")) {
            clazz.append(capitalize(s));
        }
        return clazz.toString();
    }

    public static String capitalize(String label) {
        char[] charArray = label.toCharArray();
        if (charArray.length > 0) {
            charArray[0] = Character.toUpperCase(charArray[0]);
        }
        return new String(charArray);
    }

    public static String getValue(String value) {
        return value == null? "" : value;
    }

    private String findResourcesPath() {
        for (Module module : ModuleManager.getInstance(project).getModules()) {
            for (ContentEntry contentEntry : ModuleRootManager.getInstance(module).getContentEntries()) {
                for (SourceFolder sourceFolder : contentEntry.getSourceFolders()) {
                    if(sourceFolder.getFile() == null)
                        continue;

                    if (VfsUtilCore.isAncestor(sourceFolder.getFile(), file, false)) {
                        if (sourceFolder.isTestSource() || sourceFolder.getFile().getPath().contains("resources")) {
                            String resourceRoot = sourceFolder.getFile().getPath();
                            return file.getPath().substring(resourceRoot.length() + 1);//Started with '/'
                        }else{
                            return file.getPath();
                        }
                    }
                }
            }
        }
        return file.getPath();
    }
}
