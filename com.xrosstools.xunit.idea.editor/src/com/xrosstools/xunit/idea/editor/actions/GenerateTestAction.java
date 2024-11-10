package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.commands.Command;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

import java.awt.event.ActionEvent;

import static com.xrosstools.xunit.idea.editor.actions.GenerateFactoryAction.*;

public class GenerateTestAction extends WorkbenchPartAction {
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

    public GenerateTestAction(UnitNodeDiagram diagram) {
        this.diagram = diagram;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer classBuf = GenerateFactoryAction.getTemplate("/template/TestCaseTemplate.txt");
        replace(classBuf, "!PACKAGE!", getValue(diagram.getPackageId()));
        replace(classBuf, "!TEST_CLASS!", toClassName(diagram.getName()));

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

        new CodeDisplayer("Generated test", classBuf.toString()).show();
    }

    @Override
    public Command createCommand() {
        return null;
    }
}