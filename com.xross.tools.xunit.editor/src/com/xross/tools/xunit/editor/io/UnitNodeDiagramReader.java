package com.xross.tools.xunit.editor.io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xross.tools.xunit.BehaviorType;
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
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeDiagram;
import com.xross.tools.xunit.editor.model.UnitNodeProperties;
import com.xross.tools.xunit.editor.model.ValidatorNode;

public class UnitNodeDiagramReader implements UnitConstants{
	private static Set<String> WRAPPED_UNITS = new HashSet<String>();
	static {
		WRAPPED_UNITS.add(DECORATOR_UNIT);
		WRAPPED_UNITS.add(ADAPTER_UNIT);
		WRAPPED_UNITS.add(VALID_UNIT);
		WRAPPED_UNITS.add(INVALID_UNIT);
		WRAPPED_UNITS.add(LOOP_UNIT);
		WRAPPED_UNITS.add(BRANCH_UNIT);
	}
	
	public UnitNodeDiagram getFromDocument(Document doc){
		UnitNodeDiagram model = new UnitNodeDiagram();
		Element root = doc.getDocumentElement();
		
		model.setPackageId(root.getAttribute(PACKAGE_ID));
		model.setName(root.getAttribute(NAME));
		model.setDescription(root.getAttribute(DESCRIPTION));
		model.setProperties(createAppProperties(doc));

		model.getUnits().addAll(readUnits(doc));
		
		return model;
	}
	
	private UnitNodeProperties createAppProperties(Document doc) {
		UnitNodeProperties configure = new UnitNodeProperties();
		if(doc.getElementsByTagName(PROPERTIES).getLength() != 0)
			setProperties(configure, doc.getElementsByTagName(PROPERTIES).item(0));
		return configure;
	}

	private List<UnitNode> readUnits(Document doc){
		NodeList unitNodes = doc.getElementsByTagName(UNITS).item(0).getChildNodes();
		
		List<UnitNode> units = new ArrayList<UnitNode>();
		for(int i = 0; i < unitNodes.getLength(); i++){
			units.add(createUnitNode(unitNodes.item(i)));
		}
		
		return units;
	}
	
	private UnitNode createUnitNode(Node node){
		if(node == null)
			return null;

		UnitNode unit = createPrimaryUnit(node);
		if(unit != null)
			return unit;
			
		if(WRAPPED_UNITS.contains(node.getNodeName()))
			unit = createUnitNode(node.getChildNodes().item(0));

		return unit;
	}
	
	private UnitNode createPrimaryUnit(Node node){
		String nodeName = node.getNodeName();
		UnitNode unit = null;
		
		if(PROCESSOR.equals(nodeName))
			unit = createProcessorNode(node);
		if(CONVERTER.equals(nodeName))
			unit = createConverterNode(node);
		if(DECORATOR.equals(nodeName))
			unit = createDecoratorNode(node);
		if(ADAPTER.equals(nodeName))
			unit = createAdapterNode(node);
		else if(VALIDATOR.equals(nodeName))
			unit = createValidatorNode(node);
		else if(LOCATOR.equals(nodeName))
			unit = createLocatorNode(node);
		else if(CHAIN.equals(nodeName))
			unit = createChainNode(node);
		else if(BI_BRANCH.equals(nodeName))
			unit = createBiBranchNode(node);
		else if(BRANCH.equals(nodeName))
			unit = createBranchNode(node);
		else if(WHILE.equals(nodeName))
			unit = createPreValidationLoopNode(node);
		else if(DO_WHILE.equals(nodeName))
			unit = createPostValidationLoopNode(node);

		if (unit == null)
			return null;

		setAttributes(unit, node);
		setProperties(unit.getProperties(), node);
		
		return unit;
	}
	
	private void setAttributes(UnitNode unit, Node node){
		unit.setName(getAttribute(node, NAME));
		unit.setDescription(getAttribute(node, DESCRIPTION));
		unit.setClassName(getAttribute(node, CLASS));
		unit.setReferenceName(getAttribute(node, REFERENCE));

		if(unit instanceof CompositeUnitNode)
			unit.setType(BehaviorType.valueOf(getAttribute(node, TYPE)));
	}
	
	private void setProperties(UnitNodeProperties properties, Node node){
		NodeList children = node.getChildNodes();
		Node propertyNode;
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(PROPERTY))
				continue;
			propertyNode = children.item(i);
			properties.addProperty(getAttribute(propertyNode, KEY), getAttribute(propertyNode, VALUE));
		}
	}
	
	private UnitNode createChildNode(Node node, String name){
		NodeList children = node.getChildNodes();
		Node found = null;
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(name))
				continue;
			found = children.item(i);
			break;
		}
		return createUnitNode(found);
	}
	
	private UnitNode createProcessorNode(Node node){
		UnitNode unit = new ProcessorNode();
		return unit;
	}
	
	private UnitNode createConverterNode(Node node){
		UnitNode unit = new ConverterNode();
		return unit;
	}
	
	private ValidatorNode createValidatorNode(Node node){
		ValidatorNode unit = new ValidatorNode();
		
		unit.setValidLabel(getAttribute(node, VALID_LABEL));
		unit.setInvalidLabel(getAttribute(node, INVALID_LABEL));
		
		return unit;
	}
	
	private LocatorNode createLocatorNode(Node node){
		LocatorNode unit = new LocatorNode();
		unit.setDefaultKey(getAttribute(node, DEFAULT_KEY));
		return unit;
	}

	private UnitNode createDecoratorNode(Node node){
		DecoratorNode unit = new DecoratorNode();
		
		unit.setUnit(createChildNode(node, DECORATOR_UNIT));
		return unit;
	}

	private UnitNode createAdapterNode(Node node){
		AdapterNode unit = new AdapterNode();
		
		unit.setUnit(createChildNode(node, ADAPTER_UNIT));
		return unit;
	}

	private UnitNode createChainNode(Node node){
		ChainNode chain = new ChainNode(true);
		
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			chain.addUnit(createUnitNode(children.item(i)));
		}

		return chain;
	}
	
	private UnitNode createBiBranchNode(Node node){
		BiBranchNode biBranch = new BiBranchNode(true);
		
		biBranch.setValidator((ValidatorNode)createChildNode(node, VALIDATOR));
		biBranch.setValidUnit(createChildNode(node, VALID_UNIT));
		biBranch.setInvalidUnit(createChildNode(node, INVALID_UNIT));

		return biBranch;
	}
	
	private UnitNode createBranchNode(Node node){
		BranchNode branch = new BranchNode(true);
		branch.setLocator((LocatorNode)createChildNode(node, LOCATOR));

		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(BRANCH_UNIT))
				continue;

			Node found = children.item(i);
			UnitNode branchUnit = createUnitNode(found);
			String key = getAttribute(found, KEY);
			branch.addUnit(key, branchUnit);
		}

		return branch;
	}
	
	private UnitNode createPreValidationLoopNode(Node node){
		PreValidationLoopNode whileLoop = new PreValidationLoopNode(true);
		whileLoop.setValidator((ValidatorNode)createChildNode(node, VALIDATOR));
		whileLoop.setUnit(createChildNode(node, LOOP_UNIT));
		return whileLoop;
	}
	
	private UnitNode createPostValidationLoopNode(Node node){
		PostValidationLoopNode doWhileLoop = new PostValidationLoopNode(true);
		doWhileLoop.setValidator((ValidatorNode)createChildNode(node, VALIDATOR));
		doWhileLoop.setUnit(createChildNode(node, LOOP_UNIT));
		return doWhileLoop;
	}
		
	private String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		Node attNode = map.getNamedItem(attributeName);
		if(attNode == null)
			return null;
		return attNode.getNodeValue();
		
//		for(int i = 0; i < map.getLength(); i++)
//			if(attributeName.equals(map.item(i).getNodeName()))
//				return map.item(i).getNodeValue();

//		return null;
	}
}
