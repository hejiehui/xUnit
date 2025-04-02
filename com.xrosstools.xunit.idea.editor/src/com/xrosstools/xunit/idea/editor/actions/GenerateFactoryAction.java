package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.AbstractCodeGenerator;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

import java.time.ZonedDateTime;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateFactoryAction extends AbstractCodeGenerator {
    private static final String MODEL_DESCRIPTION =
            "\n    %s\n";


    private static final String UNIT_COMMENTS =
            "    //%s\n";

    private static final String PROCESSOR_DEF =
            "    public static ProcessorCreator %s = processorCreator(\"%s\");\n\n"; //xunit id

    private static final String CONVERTER_DEF =
            "    public static ConverterCreator %s = converterCreator(\"%s\");\n\n"; //xunit id

    private static final String PROCESSOR_FACTORY =
            "    private static ProcessorCreator processorCreator(String id) {\n" +
            "        return load().processorCreator(id);\n"+
            "    }\n";

    private static final String CONVERTER_FACTORY =
            "    private static ConverterCreator converterCreator(String id) {\n" +
            "        return load().converterCreator(id);\n"+
            "    }\n";

    private Project project;
    private VirtualFile file;
    private UnitNodeDiagram diagram;

    public GenerateFactoryAction(Project project, VirtualFile file){
        super(project, "Generate model factory");
        this.project = project;
        this.file = file;
    }

    public void setDiagram(UnitNodeDiagram diagram) {
        this.diagram = diagram;
    }

    public String getDefaultFileName() {
        return toClassName(diagram.getName());
    }

    @Override
    public String getContent(String packageName, String fileName) {
        StringBuffer codeBuf = getTemplate("/template/FactoryTemplate.txt", getClass());
        replace(codeBuf, "!PACKAGE!", getValue(packageName));

        String description = getValue(diagram.getDescription()).equals("") ? "": String.format(MODEL_DESCRIPTION, getValue(diagram.getDescription()));

        replace(codeBuf, "!DESCRIPTION!", description);

        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", fileName);
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));

        replace(codeBuf, "!XUNIT_DEFINITIONS!", generateBody());
        return codeBuf.toString();
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
}
