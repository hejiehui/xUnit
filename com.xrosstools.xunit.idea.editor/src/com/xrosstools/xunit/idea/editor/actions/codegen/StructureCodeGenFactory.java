package com.xrosstools.xunit.idea.editor.actions.codegen;

import com.xrosstools.idea.gef.actions.codegen.GeneratorFactory;
import com.xrosstools.idea.gef.actions.codegen.SimpleImplGenerator;
import com.xrosstools.xunit.idea.editor.model.StructureType;

public class StructureCodeGenFactory extends GeneratorFactory {
    public static final String CHAIN = "Chain";

    private static final String CHAIN_BODY =
                    "    @Override\n" +
                    "    public void add(Unit unit) {\n" +
                    "    }";

    public static final String BI_BRANCH = "BiBranch";

    private static final String[] BI_BRANCH_BODY = new String[] {
                    "    @Override\n" +
                    "    public void setValidator(Validator validator) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void setValidUnit(Unit unit) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void setInvalidUnit(Unit unit) {\n" +
                    "    }",
    };

    public static final String BRANCH = "Branch";

    private static final String[] BRANCH_BODY = new String[] {
                    "    @Override\n" +
                    "    public void setLocator(Locator locator) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void add(String key, Unit unit) {\n" +
                    "    }",
    };

    public static final String PARALLEL_BRANCH = "ParallelBranch";

    private static final String[] PARALLEL_BRANCH_BODY = new String[] {
                    "    @Override\n" +
                    "    public void setDispatcher(Dispatcher dispatcher) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void dd(String taskId, TaskType type, Unit unit) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void validate() {\n" +
                    "    }",
    };

    public static final String PRE_VALIDATION_LOOP = "PreValidationLoop";

    private static final String[] VALIDATION_LOOP_BODY = new String[] {
                    "    @Override\n" +
                    "    public void setValidator(Validator validator) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void setUnit(Unit unit) {\n" +
                    "    }",
    };

    public static final String POST_VALIDATION_LOOP = "PostValidationLoop";

    public static final String ADAPTER = "Adapter";

    private static final String ADAPTER_BODY =
                    "    @Override\n" +
                    "    public void add(Unit unit) {\n" +
                    "    }";

    public static final String DECORATOR = "Decorator";

    private static final String[] DECORATOR_BODY = new String[]{
                    "    @Override\n" +
                    "    public void before(Context ctx) {\n" +
                    "    }",
                    "    @Override\n" +
                    "    public void after(Context ctx) {\n" +
                    "    }",
    };

    public static final StructureCodeGenFactory INSTANCE = new StructureCodeGenFactory();

    public StructureCodeGenFactory() {
        register(StructureType.chain, new SimpleImplGenerator().setInterfaceName(CHAIN).setMethods(CHAIN_BODY));
        register(StructureType.bi_branch, new SimpleImplGenerator().setInterfaceName(BI_BRANCH).setMethods(BI_BRANCH_BODY));
        register(StructureType.branch, new SimpleImplGenerator().setInterfaceName(BRANCH).setMethods(BRANCH_BODY));
        register(StructureType.parallel_branch, new SimpleImplGenerator().setInterfaceName(PARALLEL_BRANCH).setMethods(PARALLEL_BRANCH_BODY));
        register(StructureType.while_loop, new SimpleImplGenerator().setInterfaceName(PRE_VALIDATION_LOOP).setMethods(VALIDATION_LOOP_BODY));
        register(StructureType.do_while_loop, new SimpleImplGenerator().setInterfaceName(POST_VALIDATION_LOOP).setMethods(VALIDATION_LOOP_BODY));
        register(StructureType.adapter, new SimpleImplGenerator().setInterfaceName(ADAPTER).setMethods(ADAPTER_BODY));
        register(StructureType.decorator, new SimpleImplGenerator().setInterfaceName(DECORATOR).setMethods(DECORATOR_BODY));
    }
}
