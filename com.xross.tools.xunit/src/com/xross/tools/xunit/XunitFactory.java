package com.xross.tools.xunit;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xross.tools.xunit.def.AdapterDef;
import com.xross.tools.xunit.def.BiBranchDef;
import com.xross.tools.xunit.def.BranchDef;
import com.xross.tools.xunit.def.ChainDef;
import com.xross.tools.xunit.def.DecoratorDef;
import com.xross.tools.xunit.def.LocatorDef;
import com.xross.tools.xunit.def.LoopDef;
import com.xross.tools.xunit.def.UnitDef;
import com.xross.tools.xunit.def.UnitDefRepo;
import com.xross.tools.xunit.def.ValidatorDef;

public class XunitFactory implements XunitConstants {
	public static XunitFactory load(URL url) throws Exception {
        return load(url.openStream());
	}
	
	/**
	 * It will first check model file from file path, if it does not exist, it will try classpath then. 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static XunitFactory load(String path) throws Exception {
		InputStream in;
		File f = new File(path);
		if(f.exists())
			in = new FileInputStream(f);
		else {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = XunitFactory.class.getClassLoader();
			}
			in = classLoader.getResource(path).openStream();
		}
		
		return load(in);
	}
	
	public static XunitFactory load(InputStream in) throws Exception {
		XunitFactory factory = null;
		try{
			Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			factory = getFromDocument(doc);
		} finally {
			try{
				if(in != null)
					in.close();
			}catch(Throwable e1){
			}
		}
		return factory;
	}
	
	private static Set<String> WRAPPED_UNITS = new HashSet<String>();
	static {
		WRAPPED_UNITS.add(DECORATOR_UNIT);
		WRAPPED_UNITS.add(ADAPTER_UNIT);
		WRAPPED_UNITS.add(VALID_UNIT);
		WRAPPED_UNITS.add(INVALID_UNIT);
		WRAPPED_UNITS.add(LOOP_UNIT);
		WRAPPED_UNITS.add(BRANCH_UNIT);
	}
	
	private String packageId;
	private String name;
	private UnitDefRepo defRepo;
	
	public String getPackageId() {
		return packageId;
	}

	public String getName() {
		return name;
	}

	public Unit getUnit(String id) throws Exception{
		return defRepo.getUnit(id);
	}
	
	public Processor getProcessor(String id) throws Exception{
		return (Processor)getUnit(id);
	}

	public Converter getConverter(String id) throws Exception{
		return (Converter )getUnit(id);
	}

	private static XunitFactory getFromDocument(Document doc){
		XunitFactory factory = new XunitFactory();
		Element root = doc.getDocumentElement();
		factory.packageId = root.getAttribute(PACKAGE_ID);
		factory.name = root.getAttribute(NAME);
		factory.defRepo = new UnitDefRepo(factory.createApplicationProperties(doc));
		factory.readUnits(doc);
		
		return factory;
	}
	
	private Map<String, String> createApplicationProperties(Document doc){
		if(doc.getElementsByTagName(PROPERTIES).getLength() == 0)
			return Collections.emptyMap();
		
		return getProperties(doc.getElementsByTagName(PROPERTIES).item(0));
	}
	
	private void readUnits(Document doc){
		NodeList unitNodes = doc.getElementsByTagName(UNITS).item(0).getChildNodes();
		
		for(int i = 0; i < unitNodes.getLength(); i++){
			String key = getAttribute(unitNodes.item(i), NAME);
			UnitDef unitDef = createUnitNode(unitNodes.item(i));
			defRepo.put(key, unitDef);
		}
	}
	
	private UnitDef createUnitNode(Node node){
		if(node == null)
			return null;

		UnitDef unitDef = createPrimaryUnit(node);
		if(unitDef != null)
			return unitDef;
			
		if(WRAPPED_UNITS.contains(node.getNodeName()))
			unitDef = createUnitNode(node.getChildNodes().item(0));

		return unitDef;
	}
	
	private UnitDef createPrimaryUnit(Node node){
		String nodeName = node.getNodeName();
		UnitDef unitDef = null;
		
		if(PROCESSOR.equals(nodeName))
			unitDef = createProcessorNode(node);
		if(CONVERTER.equals(nodeName))
			unitDef = createConverterNode(node);
		if(DECORATOR.equals(nodeName))
			unitDef = createDecoratorNode(node);
		if(ADAPTER.equals(nodeName))
			unitDef = createAdapterNode(node);
		else if(VALIDATOR.equals(nodeName))
			unitDef = createValidatorNode(node);
		else if(LOCATOR.equals(nodeName))
			unitDef = createLocatorNode(node);
		else if(CHAIN.equals(nodeName))
			unitDef = createChainNode(node);
		else if(BI_BRANCH.equals(nodeName))
			unitDef = createBiBranchNode(node);
		else if(BRANCH.equals(nodeName))
			unitDef = createBranchNode(node);
		else if(WHILE.equals(nodeName))
			unitDef = createPreValidationLoopNode(node);
		else if(DO_WHILE.equals(nodeName))
			unitDef = createPostValidationLoopNode(node);

		if (unitDef == null)
			return null;

		unitDef.setUnitDefRepo(defRepo);
		unitDef.setName(getAttribute(node, NAME));
		unitDef.setDescription(getAttribute(node, DESCRIPTION));
		unitDef.setClassName(getAttribute(node, CLASS));
		unitDef.setReferenceName(getAttribute(node, REFERENCE));
		unitDef.setType(getType(node));
		unitDef.setProperties(getProperties(node));
		
		return unitDef;
	}
	
	private LinkedHashMap<String, String> getProperties(Node node){
		NodeList children = node.getChildNodes();
		Node entryNode;
		LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();
		
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(PROPERTY))
				continue;
			entryNode = children.item(i);
			properties.put(getAttribute(entryNode, KEY), getAttribute(entryNode, VALUE));
		}
		
		return properties;
	}
	
	private BehaviorType getType(Node node){
		String nodeName = node.getNodeName();
		if(PROCESSOR.equals(nodeName))
			return BehaviorType.processor;
		if(CONVERTER.equals(nodeName))
			return BehaviorType.converter;
		if(LOCATOR.equals(nodeName))
			return BehaviorType.locator;
		if(VALIDATOR.equals(nodeName))
			return BehaviorType.validator;
		
		return BehaviorType.valueOf(getAttribute(node, TYPE));
	}
	
	private UnitDef createChildNode(Node node, String name){
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
	
	private UnitDef createProcessorNode(Node node){
		UnitDef unitDef = new UnitDef();
		
		return unitDef;
	}
	
	private UnitDef createConverterNode(Node node){
		UnitDef unitDef = new UnitDef();
		
		return unitDef;
	}
	
	private UnitDef createDecoratorNode(Node node){
		DecoratorDef decoratorDef = new DecoratorDef();
		
//		unitDef.setType(UnitType.valueOf(getAttribute(node, TYPE)));
		decoratorDef.setClassName(getAttribute(node, CLASS));
		decoratorDef.setReferenceName(getAttribute(node, REFERENCE));
		decoratorDef.setUnitDef(createChildNode(node, DECORATOR_UNIT));
		
		return decoratorDef;
	}
	
	private UnitDef createAdapterNode(Node node){
		AdapterDef adapterDef = new AdapterDef();
		
		adapterDef.setType(BehaviorType.valueOf(getAttribute(node, TYPE)));
		adapterDef.setClassName(getAttribute(node, CLASS));
		adapterDef.setReferenceName(getAttribute(node, REFERENCE));
		adapterDef.setUnitDef(createChildNode(node, ADAPTER_UNIT));
		
		return adapterDef;
	}
	
	private UnitDef createValidatorNode(Node node){
		ValidatorDef unitDef = new ValidatorDef();
		unitDef.setValidLabel(getAttribute(node, VALID_LABEL));
		unitDef.setInvalidLabel(getAttribute(node, VALID_LABEL));
		return unitDef;
	}
	
	private UnitDef createLocatorNode(Node node){
		LocatorDef locatorDef = new LocatorDef();
		locatorDef.setDefaultKey(getAttribute(node, DEFAULT_KEY));
		return locatorDef;
	}

	private UnitDef createChainNode(Node node){
		ChainDef chainDef = new ChainDef();
		
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			chainDef.addUnitDef(createUnitNode(children.item(i)));
		}

		return chainDef;
	}
	
	private UnitDef createBiBranchNode(Node node){
		BiBranchDef biBranchDef = new BiBranchDef();
		
		biBranchDef.setValidatorDef((ValidatorDef)createChildNode(node, VALIDATOR));
		biBranchDef.setValidUnitDef(createChildNode(node, VALID_UNIT));
		biBranchDef.setInvalidUnitDef(createChildNode(node, INVALID_UNIT));

		return biBranchDef;
	}
	
	private UnitDef createBranchNode(Node node){
		BranchDef branchDef = new BranchDef();
		branchDef.setLocatorDef(createChildNode(node, LOCATOR));

		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			if(!children.item(i).getNodeName().equalsIgnoreCase(BRANCH_UNIT))
				continue;

			Node found = children.item(i);
			UnitDef branchUnit = createUnitNode(found);
			String key = getAttribute(found, KEY);
			branchUnit.setKey(key);
			branchDef.addBranchUnitDef(branchUnit);
		}

		return branchDef;
	}
	
	private UnitDef createPreValidationLoopNode(Node node){
		LoopDef whileLoop = new LoopDef(true);
		whileLoop.setValidatorDef((ValidatorDef)createChildNode(node, VALIDATOR));
		whileLoop.setUnitDef(createChildNode(node, LOOP_UNIT));
		return whileLoop;
	}
	
	private UnitDef createPostValidationLoopNode(Node node){
		LoopDef doWhileLoop = new LoopDef(false);
		doWhileLoop.setValidatorDef((ValidatorDef)createChildNode(node, VALIDATOR));
		doWhileLoop.setUnitDef(createChildNode(node, LOOP_UNIT));
		return doWhileLoop;
	}
		
	private String getAttribute(Node node, String attributeName){
		NamedNodeMap map = node.getAttributes();
		for(int i = 0; i < map.getLength(); i++)
			if(attributeName.equals(map.item(i).getNodeName()))
				return map.item(i).getNodeValue();

		return null;
	}
}