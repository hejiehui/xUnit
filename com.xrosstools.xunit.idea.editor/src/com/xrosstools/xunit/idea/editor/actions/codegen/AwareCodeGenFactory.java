package com.xrosstools.xunit.idea.editor.actions.codegen;

import com.xrosstools.idea.gef.actions.CodeGenHelper;
import com.xrosstools.idea.gef.actions.codegen.GeneratorFactory;
import com.xrosstools.idea.gef.actions.codegen.SimpleImplGenerator;
import com.xrosstools.xunit.idea.editor.model.UnitNode;

import java.util.Set;

public class AwareCodeGenFactory extends GeneratorFactory {
    public static final String UNIT_PROPERTIES_AWARE = "UnitPropertiesAware";

    private static final String UNIT_PROPERTIES_AWARE_BODY =
                    "    @Override\n" +
                    "    public void setUnitProperties(Map<String, String> properties) {\n" +
                    "    }";

    public static final String UNIT_DEFINITION_AWARE = "UnitDefinitionAware";

    private static final String UNIT_DEFINITION_AWARE_BODY =
                    "    @Override\n" +
                    "    public void setUnitDefinition(UnitDefinition unitDef) {\n" +
                    "    }";

    public static final String APPLICATION_PROPERTIES_AWARE = "ApplicationPropertiesAware";

    private static final String APPLICATION_PROPERTIES_AWARE_BODY =
                    "    @Override\n" +
                    "    public void setApplicationProperties(Map<String, String> properties) {\n" +
                    "    }";

    private static final String IMPORTS = "java.util.Map";

    public static final String[] AWARES = new String[] {UNIT_PROPERTIES_AWARE, UNIT_DEFINITION_AWARE, APPLICATION_PROPERTIES_AWARE};

    public static final AwareCodeGenFactory INSTANCE = new AwareCodeGenFactory();

    public AwareCodeGenFactory() {
        register(UNIT_PROPERTIES_AWARE, new UnitPropertiesAwareCodeGen().setInterfaceName(UNIT_PROPERTIES_AWARE).setMethods(UNIT_PROPERTIES_AWARE_BODY).setImports(IMPORTS));
        register(APPLICATION_PROPERTIES_AWARE, new SimpleImplGenerator().setInterfaceName(APPLICATION_PROPERTIES_AWARE).setMethods(APPLICATION_PROPERTIES_AWARE_BODY).setImports(IMPORTS));
        register(UNIT_DEFINITION_AWARE, new SimpleImplGenerator().setInterfaceName(UNIT_DEFINITION_AWARE).setMethods(UNIT_DEFINITION_AWARE_BODY));
    }

    private static class UnitPropertiesAwareCodeGen extends SimpleImplGenerator {
        private static final String FIELD_TEMPLATE = "private static final String %s = \"%s\";";

        public <T> String[] generateFields(T model) {
            String[] fields = EMPTY;
            if(model instanceof UnitNode) {
                Set<String> names = ((UnitNode)model).getProperties().getNames();
                fields = new String[names.size()];
                int i = 0;
                for(String name: names) {
                    fields[i++] = String.format(FIELD_TEMPLATE, CodeGenHelper.toConstantName(name), name);
                }
            }
            return fields;
        }
    }
}
