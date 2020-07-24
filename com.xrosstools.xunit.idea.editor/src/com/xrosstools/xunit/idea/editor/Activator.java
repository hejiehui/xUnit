package com.xrosstools.xunit.idea.editor;

import com.xrosstools.xunit.idea.editor.model.*;

import java.util.HashMap;
import java.util.Map;

public class Activator {
    public static final String PLUGIN_ID	= "com.xrosstools.xunit.editor";	//$NON-NLS-1$

    public static final String HOME = "/icons/";
    public static final String ICO = ".png";
    public static final String JPG = ".jpg";

    public static final String START_POINT = "start_point";
    public static final String END_POINT = "end_point";
    public static final String NODE = "jar_empty";

    public static final String PROCESSOR = BehaviorType.processor.name();
    public static final String CONVERTER = BehaviorType.converter.name();
    public static final String VALIDATOR = BehaviorType.validator.name();//"arrow_split";
    public static final String LOCATOR = BehaviorType.locator.name();//"arrow_move";
    public static final String DISPATCHER = BehaviorType.dispatcher.name();//"arrow_split";

    public static final String DECORATOR = StructureType.decorator.name();
    public static final String ADAPTER = StructureType.adapter.name();
    public static final String CHAIN = StructureType.chain.name();//"link";
    public static final String BI_BRANCH = StructureType.bi_branch.name();//"node_insert_next";
    public static final String BRANCH = StructureType.branch.name();//"wallpapers";
    public static final String WHILE = StructureType.while_loop.name();//"stock_right_with_subpoints";
    public static final String DO_WHILE = StructureType.do_while_loop.name();//"stock_left_with_subpoints";
    public static final String PARALLEL_BRANCH = StructureType.parallel_branch.name();

    public static String getIconPath(String iconId) {
        return HOME + iconId + ICO;
    }

    private static Map<Class, String> reg = new HashMap<>();

    public static String getIconPath(Class clazz){
        String iconId = reg.get(clazz);
        return HOME + iconId + ICO;
    }

    static {
        reg.put(UnitNodeDiagram.class, CHAIN);
        reg.put(StartPointNode.class, END_POINT);
        reg.put(EndPointNode.class, END_POINT);
        reg.put(ValidatorNode.class, VALIDATOR);
        reg.put(LocatorNode.class, LOCATOR);
        reg.put(DispatcherNode.class, DISPATCHER);

        reg.put(ProcessorNode.class, PROCESSOR);
        reg.put(ConverterNode.class, CONVERTER);
//    	reg.put(UnitNode.class, NODE);

        reg.put(DecoratorNode.class, DECORATOR);
        reg.put(AdapterNode.class, ADAPTER);
        reg.put(ChainNode.class, CHAIN);
        reg.put(BiBranchNode.class, BI_BRANCH);
        reg.put(BranchNode.class, BRANCH);
        reg.put(ParallelBranchNode.class, PARALLEL_BRANCH);
        reg.put(PreValidationLoopNode.class, WHILE);
        reg.put(PostValidationLoopNode.class, DO_WHILE);
    }
}
