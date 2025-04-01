package com.xrosstools.xunit.idea.editor.model;

import com.xrosstools.idea.gef.figures.ColorConstants;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public interface UnitConstants extends XunitConstants {
    Color ADAPTER_TITLE_COLOR = ColorConstants.darkGreen;
    Color DECORATOR_TITLE_COLOR = ColorConstants.lightGray;

    int TOP_LEVEL_SPACE = 10;
    int LINK_HANDLE = 30;
    int H_NODE_SPACE = 50;
    int V_NODE_SPACE = 50;
    int BORDER_WIDTH = 5;
    int H_BORDER_WIDTH = 10;
    int V_BORDER_WIDTH = 15;
    Dimension NODE_SIZE = new Dimension(100, 50);

    int INDEX_VALID = 0;
    int INDEX_INVALID = 1;
    int INDEX_UNIT = 0;
    int INDEX_NOT = -1;

    String SEPARATER = " : ";

    String MSG_EMPTY = "empty!";
    String MSG_DEFAULT = "default";
    String MSG_NOT_SPECIFIED = "not specified";
    String DEFAULT_VALID_LABEL = "true";
    String DEFAULT_INVALID_LABEL = "false";
    long DEFAULT_TIMEOUT = 1;
    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    TaskType DEFAULT_TASK_TYPE = TaskType.normal;

    String EMPTY_VALUE = "";
    String PROP_NODE = "PROP_NODE";
    String PROP_LOCATION = "PROP_LOCATION";
    String PROP_SIZE = "PROP_SIZE";
    String PROP_INPUTS = "PROP_INPUTS";
    String PROP_OUTPUTS = "PROP_OUTPUTS";

    String CATEGORY_COMMON = "Common";
    String CATEGORY_OPTIONAL = "Optional";

    String PROP_PACKAGE = "Package";
    String PROP_NAME = "Name";
    String PROP_DESCRIPTION = "Description";
    String PROP_BEHAVIOR_TYPE = "Behavior type";
    String PROP_STRUCTURE_TYPE = "Structure type";
    String PROP_CLASS = "Class";
    String PROP_REFERENCE = "Reference unit";
    String PROP_MODULE = "Reference Module";
    String PROP_VALID_LABEL = "validLabel";
    String PROP_INVALID_LABEL = "invalidLabel";
    String PROP_KEY = "key";
    String PROP_LABEL = "Label";
    String PROP_DEFAULT_KEY = "Default key";
    String PROP_TIMEOUT = "timeout";
    String PROP_TIME_UNIT = "time unit";
    String PROP_COMPLETION_MODE= "Completion Mode";
    String PROP_TASK_ID = "Task Id";
    String PROP_TASK_TYPE= "Task Type";

    String DEFAULT_PRIMARY_IMPL = "com.xrosstools.xunit.impl.DefaultUnitImpl";
    String DEFAULT_CHAIN_IMPL = "com.xrosstools.xunit.impl.ChainImpl";
    String DEFAULT_BI_BRANCH_IMPL = "com.xrosstools.xunit.impl.BiBranchImpl";
    String DEFAULT_BRANCH_IMPL = "com.xrosstools.xunit.impl.BranchImpl";
    String DEFAULT_PARALLEL_BRANCH_IMPL = "com.xrosstools.xunit.impl.ParallelBranchImpl";
    String DEFAULT_WHILE_LOOP_IMPL = "com.xrosstools.xunit.impl.PreValidationLoopImpl";
    String DEFAULT_DO_WHILE_LOOP_IMPL = "com.xrosstools.xunit.impl.PostValidationLoopImpl";
    String DEFAULT_DECORATOR_IMPL = DEFAULT_PRIMARY_IMPL;
    String DEFAULT_ADAPTER_IMPL = DEFAULT_PRIMARY_IMPL;

    String GENERATE_FACTORY = "Generate factory";
    String GENERATE_TEST = "Generate test";
}
