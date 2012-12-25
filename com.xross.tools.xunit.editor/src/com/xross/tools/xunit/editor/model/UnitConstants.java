package com.xross.tools.xunit.editor.model;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;

import com.xross.tools.xunit.XrossUnitConstants;
import com.xross.tools.xunit.impl.BiBranchImpl;
import com.xross.tools.xunit.impl.BranchImpl;
import com.xross.tools.xunit.impl.ChainImpl;
import com.xross.tools.xunit.impl.DefaultUnitImpl;
import com.xross.tools.xunit.impl.PostValidationLoopImpl;
import com.xross.tools.xunit.impl.PreValidationLoopImpl;

//TODO move common constants into runtime
public interface UnitConstants extends XrossUnitConstants {
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
	
	String EMPTY_VALUE = "";
	String PROP_NODE = "PROP_NODE";
	String PROP_LOCATION = "PROP_LOCATION";
	String PROP_SIZE = "PROP_SIZE";
	String PROP_INPUTS = "PROP_INPUTS";
	String PROP_OUTPUTS = "PROP_OUTPUTS";
	
	String CATEGORY_COMMON = "Common";
	String CATEGORY_OPTIONAL = "Optional";
	
	String PROP_NAME = "Name";
	String PROP_DESCRIPTION = "Description";
	String PROP_BEHAVIOR_TYPE = "Behavior type";
	String PROP_STRUCTURE_TYPE = "Structure type";
	String PROP_CLASS = "Class";
	String PROP_REFERENCE = "Reference unit";
	String PROP_VALID_LABEL = "validLabel";
	String PROP_INVALID_LABEL = "invalidLabel";
	String PROP_LABEL = "Label";
	String PROP_DEFAULT_KEY = "Default key";
	
	String DEFAULT_PRIMARY_IMPL = DefaultUnitImpl.class.getName();
	String DEFAULT_CHAIN_IMPL = ChainImpl.class.getName();
	String DEFAULT_BI_BRANCH_IMPL = BiBranchImpl.class.getName();
	String DEFAULT_BRANCH_IMPL = BranchImpl.class.getName();
	String DEFAULT_WHILE_LOOP_IMPL = PreValidationLoopImpl.class.getName();
	String DEFAULT_DO_WHILE_LOOP_IMPL = PostValidationLoopImpl.class.getName();
	String DEFAULT_DECORATOR_IMPL = DEFAULT_PRIMARY_IMPL;
	String DEFAULT_ADAPTER_IMPL = DEFAULT_PRIMARY_IMPL;
}
