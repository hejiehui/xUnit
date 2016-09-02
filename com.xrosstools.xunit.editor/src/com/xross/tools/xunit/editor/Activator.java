package com.xross.tools.xunit.editor;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.xross.tools.xunit.editor.model.AdapterNode;
import com.xross.tools.xunit.editor.model.BiBranchNode;
import com.xross.tools.xunit.editor.model.BranchNode;
import com.xross.tools.xunit.editor.model.ChainNode;
import com.xross.tools.xunit.editor.model.ConverterNode;
import com.xross.tools.xunit.editor.model.DecoratorNode;
import com.xross.tools.xunit.editor.model.EndPointNode;
import com.xross.tools.xunit.editor.model.LocatorNode;
import com.xross.tools.xunit.editor.model.PostValidationLoopNode;
import com.xross.tools.xunit.editor.model.PreValidationLoopNode;
import com.xross.tools.xunit.editor.model.ProcessorNode;
import com.xross.tools.xunit.editor.model.StartPointNode;
import com.xross.tools.xunit.editor.model.StructureType;
import com.xross.tools.xunit.editor.model.ValidatorNode;
import com.xrosstools.xunit.BehaviorType;

public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID	= "com.xrosstools.xunit.editor";	//$NON-NLS-1$
	
	public static final String HOME = "icons/";
	public static final String ICO = ".ico";
	public static final String JPG = ".jpg";
	
	public static final String START_POINT = "start_point";
	public static final String END_POINT = "end_point";
	public static final String NODE = "jar_empty";

	public static final String PROCESSOR = BehaviorType.processor.name();
	public static final String CONVERTER = BehaviorType.converter.name();
	public static final String VALIDATOR = BehaviorType.validator.name();//"arrow_split";
	public static final String LOCATOR = BehaviorType.locator.name();//"arrow_move";
	
	public static final String DECORATOR = StructureType.decorator.name();
	public static final String ADAPTER = StructureType.adapter.name();
	public static final String CHAIN = StructureType.chain.name();//"link";
	public static final String BI_BRANCH = StructureType.bi_branch.name();//"node_insert_next";
	public static final String BRANCH = StructureType.branch.name();//"wallpapers";
	public static final String WHILE = StructureType.while_loop.name();//"stock_right_with_subpoints";
	public static final String DO_WHILE = StructureType.do_while_loop.name();//"stock_left_with_subpoints";

	// The shared instance
	private static Activator	plugin;
	private static BundleContext s_context;

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		s_context = context;
	}

	public void stop(BundleContext context) throws Exception {
		s_context = null;
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

    public static BundleContext getContext()
    {
        return s_context;
    }
    
    protected void initializeImageRegistry(ImageRegistry reg) {
    	put(reg, START_POINT);
    	put(reg, END_POINT);
    	put(reg, VALIDATOR);
    	put(reg, LOCATOR);
    	put(reg, NODE);
    	put(reg, PROCESSOR);
    	put(reg, CONVERTER);
    	put(reg, DECORATOR);
    	put(reg, ADAPTER);
    	put(reg, CHAIN);
    	put(reg, BI_BRANCH);
    	put(reg, BRANCH);
    	put(reg, WHILE);
    	put(reg, DO_WHILE);
    	
    	initByClass(reg);
    }
    
    private void put(ImageRegistry reg, String id){
    	reg.put(id, imageDescriptorFromPlugin(PLUGIN_ID, getIconPath(id)));
    }
    
    private void put(ImageRegistry reg, Class clazz, String id){
    	reg.put(getName(clazz), imageDescriptorFromPlugin(PLUGIN_ID, getIconPath(id)));
    }
    
    private void initByClass(ImageRegistry reg){
//    	reg.put(getName(UnitNodeDiagram.class), imageDescriptorFromPlugin(PLUGIN_ID, END_POINT));
    	put(reg, StartPointNode.class, END_POINT);
    	put(reg, EndPointNode.class, END_POINT);
    	put(reg, ValidatorNode.class, VALIDATOR);
    	put(reg, LocatorNode.class, LOCATOR);

    	put(reg, ProcessorNode.class, PROCESSOR);
    	put(reg, ConverterNode.class, CONVERTER);
//    	put(reg, UnitNode.class, NODE);
    	
    	put(reg, DecoratorNode.class, DECORATOR);
    	put(reg, AdapterNode.class, ADAPTER);
    	put(reg, ChainNode.class, CHAIN);
    	put(reg, BiBranchNode.class, BI_BRANCH);
    	put(reg, BranchNode.class, BRANCH);
    	put(reg, PreValidationLoopNode.class, WHILE);
    	put(reg, PostValidationLoopNode.class, DO_WHILE);
    }
    
    public Image getImage(Class clazz){
    	return this.getImageRegistry().get(getName(clazz));
    }
    
    private static String getIconPath(String iconId){
    	return HOME + iconId + ICO;
    }
    
    private String getName(Class clazz){
    	return clazz.getSimpleName();
    }
}
