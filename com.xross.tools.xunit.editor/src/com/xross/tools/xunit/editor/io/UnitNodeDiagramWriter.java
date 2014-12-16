package com.xross.tools.xunit.editor.io;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.xross.tools.xunit.editor.model.AdapterNode;
import com.xross.tools.xunit.editor.model.BiBranchNode;
import com.xross.tools.xunit.editor.model.BranchNode;
import com.xross.tools.xunit.editor.model.ChainNode;
import com.xross.tools.xunit.editor.model.CompositeUnitNode;
import com.xross.tools.xunit.editor.model.ConverterNode;
import com.xross.tools.xunit.editor.model.DecoratorNode;
import com.xross.tools.xunit.editor.model.LocatorNode;
import com.xross.tools.xunit.editor.model.PostValidationLoopNode;
import com.xross.tools.xunit.editor.model.PreValidationLoopNode;
import com.xross.tools.xunit.editor.model.ProcessorNode;
import com.xross.tools.xunit.editor.model.UnitConfigure;
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;
import com.xross.tools.xunit.editor.model.ValidatorNode;

public class UnitNodeDiagramWriter implements UnitConstants{
	public Document writeToDocument(UnitNodeDiagram model){
		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = (Element)doc.createElement(XUNIT);
			doc.appendChild(root);

			appendDescription(doc, root, model);
			appendImports(doc, root, model);
			root.appendChild(createConfigure(doc, model.getConfigure()));
			
			Element unitsNode = (Element)doc.createElement(UNITS);
			root.appendChild(unitsNode);
			for(UnitNode unit: model.getUnits())
				unitsNode.appendChild(createUnitNode(doc, unit));

			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void appendDescription(Document doc, Element root, UnitNodeDiagram model){
		root.setAttribute(PACKAGE_ID, model.getPackageId());
		root.setAttribute(NAME, model.getName());
		root.setAttribute(DESCRIPTION, model.getDescription());
//		if(model.getDescription() == null)
//			return;
//		
//		Element commentsNode = (Element)doc.createElement(DESCRIPTION);
//		commentsNode.appendChild(doc.createTextNode(model.getDescription()));
//		root.appendChild(commentsNode);
	}
	
	private void appendImports(Document doc, Element root, UnitNodeDiagram model){
		if(model.getImports().size() == 0)
			return;
		
		Element importsNode = (Element)doc.createElement(IMPORTS);
		root.appendChild(importsNode);
		for(String importStr: model.getImports()){
			Element importNode = (Element)doc.createElement(IMPORT);
			importNode.setAttribute(PACKAGE_ID, importStr);
			importsNode.appendChild(importNode);
		}
	}
	
	private Element createConfigure(Document doc, UnitConfigure configure){
		Element node = (Element)doc.createElement(CONFIGURE);
		for(String catName: configure.getCategoryNames()){
			node.appendChild(createCategory(doc, configure, catName));
		}
		return node;
	}
	
	private Element createCategory(Document doc, UnitConfigure configure, String catName){
		Element node = (Element)doc.createElement(CATEGORY);
		node.setAttribute(NAME, catName);
		for(String key: configure.getEntryNames(catName)){
			Element entryNode = (Element)doc.createElement(ENTRY);
			entryNode.setAttribute(KEY, key);
			entryNode.setAttribute(VALUE, configure.getEntry(catName, key));
			node.appendChild(entryNode);
		}
		return node;
	}
	
	private Element createUnitNode(Document doc, UnitNode unit){
		Element node = null;
		
		if(unit instanceof ValidatorNode)
			node = createValidatorNode(doc, (ValidatorNode)unit);
		else if(unit instanceof LocatorNode)
			node = createLocatorNode(doc, (LocatorNode)unit);
		else if(unit instanceof ChainNode)
			node = createChainNode(doc, (ChainNode)unit);
		else if(unit instanceof BiBranchNode)
			node = createBiBranchNode(doc, (BiBranchNode)unit);
		else if(unit instanceof BranchNode)
			node = createBranchNode(doc, (BranchNode)unit);
		else if(unit instanceof PreValidationLoopNode)
			node = createPreValidationLoopNode(doc, (PreValidationLoopNode)unit);
		else if(unit instanceof PostValidationLoopNode)
			node = createPostValidationLoopNode(doc, (PostValidationLoopNode)unit);
		else if(unit instanceof DecoratorNode)
			node = createDecoratorNode(doc, (DecoratorNode)unit);
		else if(unit instanceof AdapterNode)
			node = createAdapterNode(doc, (AdapterNode)unit);
		else if(unit instanceof ProcessorNode)
			node = createProcessorNode(doc, unit);
		else if(unit instanceof ConverterNode)
			node = createConverterNode(doc, unit);

		if (unit == null)
			return null;

		setAttributes(node, unit);
		
		return node;
	}
	
	private void setAttributes(Element node, UnitNode unit){
		node.setAttribute(NAME, unit.getName());
		node.setAttribute(DESCRIPTION, unit.getDescription());
		node.setAttribute(CLASS, unit.getClassName());
		node.setAttribute(REFERENCE, unit.getReferenceName());

		if(unit instanceof CompositeUnitNode)
			node.setAttribute(TYPE, unit.getType().name());
	}

	private Element createProcessorNode(Document doc, UnitNode unit){
		Element node = (Element)doc.createElement(PROCESSOR);
		return node;
	}
	
	private Element createConverterNode(Document doc, UnitNode unit){
		Element node = (Element)doc.createElement(CONVERTER);
		return node;
	}
	
	private Element createValidatorNode(Document doc, ValidatorNode unit){
		Element node = (Element)doc.createElement(VALIDATOR);
		
		node.setAttribute(VALID_LABEL, unit.getValidLabel());
		node.setAttribute(INVALID_LABEL, unit.getInvalidLabel());
		
		return node;
	}
	
	private Element createLocatorNode(Document doc, LocatorNode unit){
		Element node = (Element)doc.createElement(LOCATOR);
		node.setAttribute(DEFAULT_KEY, unit.getDefaultKey());
		
		return node;
	}

	private Element createDecoratorNode(Document doc, DecoratorNode unit){
		Element node = (Element)doc.createElement(DECORATOR);
		if(unit.getUnit() != null){
			Element childNode = (Element)doc.createElement(DECORATOR_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getUnit()));
			node.appendChild(childNode);
		}
		return node;
	}

	private Element createAdapterNode(Document doc, AdapterNode unit){
		Element node = (Element)doc.createElement(ADAPTER);
		if(unit.getUnit() != null){
			Element childNode = (Element)doc.createElement(ADAPTER_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getUnit()));
			node.appendChild(childNode);
		}
		return node;
	}
	
	private Element createChainNode(Document doc, ChainNode unit){
		Element node = (Element)doc.createElement(CHAIN);
		
		for(UnitNode child: unit.getUnits())
			node.appendChild(createUnitNode(doc, child));

		return node;
	}
	
	private Element createBiBranchNode(Document doc, BiBranchNode unit){
		Element node = (Element)doc.createElement(BI_BRANCH);
		node.appendChild(createUnitNode(doc, unit.getValidator()));
		
		if(unit.getValidUnit() != null){
			Element childNode = (Element)doc.createElement(VALID_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getValidUnit()));
			node.appendChild(childNode);
		}
		
		if(unit.getInvalidUnit() != null){
			Element childNode = (Element)doc.createElement(INVALID_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getInvalidUnit()));
			node.appendChild(childNode);
		}
		
		return node;
	}
	
	private Element createBranchNode(Document doc, BranchNode unit){
		Element node = (Element)doc.createElement(BRANCH);
		node.appendChild(createUnitNode(doc, unit.getLocator()));

		for(UnitNode child: unit.getContainerNode().getAll()){
			Element childNode = (Element)doc.createElement(BRANCH_UNIT);
			childNode.setAttribute(KEY, child.getInputLabel());
			childNode.appendChild(createUnitNode(doc, child));
			node.appendChild(childNode);
		}

		return node;
	}
	
	private Element createPreValidationLoopNode(Document doc, PreValidationLoopNode unit){
		Element node = (Element)doc.createElement(WHILE);
		node.appendChild(createUnitNode(doc, unit.getValidator()));
		
		if(unit.getUnit() != null){
			Element childNode = (Element)doc.createElement(LOOP_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getUnit()));
			node.appendChild(childNode);
		}

		return node;
	}
	
	private Element createPostValidationLoopNode(Document doc, PostValidationLoopNode unit){
		Element node = (Element)doc.createElement(DO_WHILE);
		node.appendChild(createUnitNode(doc, unit.getValidator()));
		
		if(unit.getUnit() != null){
			Element childNode = (Element)doc.createElement(LOOP_UNIT);
			childNode.appendChild(createUnitNode(doc, unit.getUnit()));
			node.appendChild(childNode);
		}

		return node;
	}
}
