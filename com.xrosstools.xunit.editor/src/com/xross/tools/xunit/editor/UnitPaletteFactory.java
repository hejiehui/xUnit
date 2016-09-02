package com.xross.tools.xunit.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;

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
import com.xross.tools.xunit.editor.model.ValidatorNode;

public class UnitPaletteFactory {
	private Class<UnitPaletteFactory> imageClass = UnitPaletteFactory.class;
    public PaletteRoot createPalette() {
        PaletteRoot paletteRoot = new PaletteRoot();
        paletteRoot.addAll(createCategories(paletteRoot));    	
        return paletteRoot;
    }
    
    private List<PaletteContainer> createCategories(PaletteRoot root) {
        List<PaletteContainer> categories = new ArrayList<PaletteContainer>();

        categories.add(createControlGroup(root));

        return categories;
    }
    
    private static Object[][] ENTRIES = new Object[][]{
    	{"Processor", ProcessorNode.class, Activator.PROCESSOR},
    	{"Converter", ConverterNode.class, Activator.CONVERTER},
    	{"Validator", ValidatorNode.class, Activator.VALIDATOR},
    	{"Locator", LocatorNode.class, Activator.LOCATOR},
    	{},
    	{"Chain", ChainNode.class, Activator.CHAIN},
    	{"If/else", BiBranchNode.class, Activator.BI_BRANCH},
    	{"Branch", BranchNode.class, Activator.BRANCH},
    	{"While loop", PreValidationLoopNode.class, Activator.WHILE},
    	{"Do while loop", PostValidationLoopNode.class, Activator.DO_WHILE},
    	{},
    	{"Start", StartPointNode.class, Activator.START_POINT},
    	{"End", EndPointNode.class, Activator.END_POINT},
    	{},
    	{"Decorator", DecoratorNode.class, Activator.DECORATOR},
    	{"Adapter", AdapterNode.class, Activator.ADAPTER},
    };

    private PaletteContainer createControlGroup(PaletteRoot root) {
        PaletteGroup controlGroup = new PaletteGroup("Control Group");
        List<PaletteEntry> entries = new ArrayList<PaletteEntry>();

        ToolEntry tool = new SelectionToolEntry();
        entries.add(tool);
        root.setDefaultEntry(tool);

    	tool = new MarqueeToolEntry();
    	entries.add(tool);

    	PaletteSeparator sep = new PaletteSeparator();
    	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
    	entries.add(sep);

    	for(Object[] entry: ENTRIES){
    		if(entry.length == 0){
//    	    	sep = new PaletteSeparator();
//    	    	sep.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
//    	    	entries.add(sep);
    	    	entries.add(new PaletteSeparator());
    		}else{
		    	entries.add(new CombinedTemplateCreationEntry(
		    			(String)entry[0],
		    			(String)entry[0],
		    			entry[1],
		    			new SimpleFactory((Class)entry[1]),
		    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2])),    			
		    			Activator.getDefault().getImageRegistry().getDescriptor(((String)entry[2]))));
    		}
    	}

    	controlGroup.addAll(entries);
        return controlGroup;
    }    

}
