package com.xrosstools.xunit.idea.editor.actions.codegen;

import com.xrosstools.idea.gef.actions.codegen.GeneratorFactory;
import com.xrosstools.idea.gef.actions.codegen.SimpleImplGenerator;
import com.xrosstools.xunit.idea.editor.model.BehaviorType;

public class BehaviorCodeGenFactory extends GeneratorFactory<BehaviorType> {
    public static final String PROCESSOR = "Processor";
    public static final String CONVERTER = "Converter";
    public static final String VALIDATOR = "Validator";
    public static final String LOCATOR = "Locator";
    public static final String DISPATCHER = "Dispatcher";

    private static final String IMPORTS = "com.xrosstools.xunit.*";

    private static final String PROCESSOR_METHOD =
                    "    @Override\n" +
                    "    public void process(Context ctx) {\n" +
                    "    }";

    private static final String CONVERTER_METHOD =
                    "    @Override\n" +
                    "    public Context convert(Context ctx) {\n" +
                    "        return null;\n"+
                    "    }";

    private static final String VALIDATOR_METHOD =
                    "    @Override\n" +
                    "    public boolean validate(Context ctx) {\n" +
                    "        return true;\n"+
                    "    }";

    private static final String LOCATOR_FIELDS =
                    "private String defaultKey = null;";

    private static final String[] LOCATOR_METHODS = new String[]{
                    "    @Override\n" +
                    "    public void setDefaultKey(String key) {\n" +
                    "        defaultKey = key;\n"+
                    "    }",

                    "    @Override\n" +
                    "    public String getDefaultKey() {\n" +
                    "        return defaultKey;\n"+
                    "    }",

                    "    @Override\n" +
                    "    public String locate(Context ctx) {\n" +
                    "        return null;\n"+
                    "    }"
    };

    private static final String[] DISPATCHER_FIELDS = new String[]{
                    "    private CompletionMode mode;",
                    "    private long timeout;",
                    "    private TimeUnit timeUnit;"
    };

    private static final String[] DISPATCHER_METHODS = new String[] {
                    "    @Override\n" +
                    "    public void setCompletionMode(CompletionMode mode) {\n" +
                    "        this.mode = mode;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public CompletionMode getCompletionMode() {\n" +
                    "        return mode;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public void setTimeout(long timeout) {\n" +
                    "        this.timeout = timeout;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public long getTimeout() {\n" +
                    "        return timeout;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public void setTimeUnit(TimeUnit timeUnit) {\n" +
                    "        this.timeUnit = timeUnit;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public TimeUnit getTimeUnit() {\n" +
                    "        return timeUnit;\n" +
                    "    }",

                    "    @Override\n" +
                    "    public Map<String, Context> dispatch(Context ctx, Map<String, TaskType> taskInfoMap) {\n" +
                    "        Map<String, Context> dispatchCtx = new HashMap<>();\n" +
                    "        return dispatchCtx;\n" +
                    "    }"
    };

    public static final BehaviorCodeGenFactory INSTANCE = new BehaviorCodeGenFactory();


    public BehaviorCodeGenFactory() {
        register(BehaviorType.processor, new SimpleImplGenerator().setInterfaceName(PROCESSOR).setMethods(PROCESSOR_METHOD).setImports(IMPORTS));
        register(BehaviorType.converter, new SimpleImplGenerator().setInterfaceName(CONVERTER).setMethods(CONVERTER_METHOD).setImports(IMPORTS));
        register(BehaviorType.validator, new SimpleImplGenerator().setInterfaceName(VALIDATOR).setMethods(VALIDATOR_METHOD).setImports(IMPORTS));
        register(BehaviorType.locator, new SimpleImplGenerator().setInterfaceName(LOCATOR).setMethods(LOCATOR_METHODS).setFields(LOCATOR_FIELDS).setImports(IMPORTS));
        register(BehaviorType.dispatcher, new SimpleImplGenerator().setInterfaceName(DISPATCHER).setMethods(DISPATCHER_METHODS).setFields(DISPATCHER_FIELDS).setImports(IMPORTS));
    }
}
