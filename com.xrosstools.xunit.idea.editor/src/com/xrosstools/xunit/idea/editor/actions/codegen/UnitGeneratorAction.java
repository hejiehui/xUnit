package com.xrosstools.xunit.idea.editor.actions.codegen;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.codegen.AbstractOptionalGeneratorAction;
import com.xrosstools.idea.gef.actions.codegen.ImplementationGenerator;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.idea.gef.util.IPropertySource;
import com.xrosstools.xunit.idea.editor.commands.AssignClassCommand;
import com.xrosstools.xunit.idea.editor.model.CompositeUnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import java.util.ArrayList;
import java.util.List;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.toClassName;

public class UnitGeneratorAction extends AbstractOptionalGeneratorAction {
    private UnitNode node;

    private BehaviorCodeGenFactory behaviorCodeGenFactory = BehaviorCodeGenFactory.INSTANCE;
    private StructureCodeGenFactory structureCodeGenFactory = StructureCodeGenFactory.INSTANCE;
    private AwareCodeGenFactory awareCodeGenFactory = AwareCodeGenFactory.INSTANCE;

    @Override
    public String getDefaultClassName(IPropertySource source, String classType) {
        String name = String.format("%s %s", node.getName(), node.getType().name());
        return toClassName(name);
    }

    @Override
    public String getOptionMessage(IPropertySource source, String classType) {
        return "Implement optional awareness";
    }

    @Override
    public String[] getOptions(IPropertySource source, String classType) {
        return AwareCodeGenFactory.AWARES;
    }

    @Override
    public List<ImplementationGenerator> getGenerators(IPropertySource source, String classType, List<String> selectedOptions) {
        List<ImplementationGenerator> generators = new ArrayList<>();
        generators.add(behaviorCodeGenFactory.getGenerator(node.getType()));

        if(node instanceof CompositeUnitNode)
            generators.add(structureCodeGenFactory.getGenerator(((CompositeUnitNode)node).getStructureType()));

        //Generate awareness code
        for(String sel: selectedOptions) {
            generators.add(awareCodeGenFactory.getGenerator(sel));
        }

        return generators;
    }

    @Override
    public Command createCommand(IPropertySource source, String classType, String fullClassName, List<String> selectedOptions) {
        return new AssignClassCommand((UnitNode)source, fullClassName);
    }

    private static String getClassType(UnitNode node) {
        return node instanceof CompositeUnitNode ? ((CompositeUnitNode)node).getStructureType().name() : node.getType().name();
    }

    public UnitGeneratorAction(Project project, UnitNode node){
        super(project, node, getClassType(node));
        this.node = node;
    }
}
