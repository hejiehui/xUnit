package com.xrosstools.xunit.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.xrosstools.idea.gef.actions.AbstractCodeGenerator;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

import java.awt.event.ActionEvent;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateTestAction extends AbstractCodeGenerator {
    private static final String UNIT_CASE_HEADER =
            "    @Test\n" +
            "    public void test%s() throws Exception {\n";//unit

    private static final String UNIT_COMMENT =
            "        //Comments: %s\n "; //unit comments

    private static final String PROCESSOR_CASE_BODY =
            "        Processor processor = %s.%s.create();\n" +  //unit
            "        Context ctx = new Context();\n" +
            "        processor.process(ctx);\n" +
            "        Object expected = null;\n" +
            "        assertEquals(expected, ctx.get());\n" + //machine, next node
            "    }\n\n";

    private static final String CONVERTER_CASE_BODY =
            "        Converter converter = %s.%s.create();\n" +  //unit
            "        Context ctx = new Context();\n" +
            "        NewContext result = (NewContext)converter.convert(ctx);\n" +
            "        Object expected = null;\n" +
            "        assertEquals(expected, result.get());\n" + //machine, next node
            "    }\n\n";

    private UnitNodeDiagram diagram;

    public void setDiagram(UnitNodeDiagram diagram) {
        this.diagram = diagram;
    }

    public String getDefaultFileName() {
        return toClassName(diagram.getName()) + "Test";
    }

    public GenerateTestAction(Project project) {
        super(project, "Generate test cases");
    }

    @Override
    public String getContent(String packageName, String fileName) {
        PsiClass factoryClass = chooseClass("Select model factory", toClassName(diagram.getName()));
        if(factoryClass == null) return  null;

        StringBuffer classBuf = getTemplate("/template/TestCaseTemplate.txt", getClass());
        replace(classBuf, "!PACKAGE!", packageName);
        replace(classBuf, "!TEST_CLASS!", fileName);
        replace(classBuf, "!FACTORY_CLASS!", factoryClass.getQualifiedName());
        StringBuffer constants = new StringBuffer();

        for(UnitNode unit: diagram.getUnits()) {
            String id = unit.getName();
            String constDef = id.contains(" ") ? id.replace(' ', '_') : id;
            constants.append(String.format(UNIT_CASE_HEADER, capitalize(constDef)));

            if(unit.getDescription() != null && unit.getDescription().trim().length() > 0)
                constants.append(String.format(UNIT_COMMENT, unit.getDescription()));

            String template = unit.getType() == BehaviorType.processor ? PROCESSOR_CASE_BODY : CONVERTER_CASE_BODY;

            constants.append(String.format(template, toClassName(diagram.getName()), constDef));
        }

        String codeBody = constants.toString();
        if(codeBody.endsWith("\n\n"))
            codeBody = codeBody.substring(0, codeBody.length()-2);

        replace(classBuf, "!TEST_BODY!", codeBody);

//        new CodeDisplayer("Generated test", classBuf.toString()).show();
        return classBuf.toString();
    }

    @Override
    public Command createCommand() {
        return null;
    }
}