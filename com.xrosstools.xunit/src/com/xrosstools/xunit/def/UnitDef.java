package com.xrosstools.xunit.def;

import java.util.LinkedHashMap;

import com.xrosstools.xunit.ApplicationPropertiesAware;
import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitSpring;
import com.xrosstools.xunit.TaskType;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.UnitDefinition;
import com.xrosstools.xunit.UnitDefinitionAware;
import com.xrosstools.xunit.UnitPropertiesAware;
import com.xrosstools.xunit.XunitFactory;
import com.xrosstools.xunit.impl.ConverterEnforcer;
import com.xrosstools.xunit.impl.DefaultUnitImpl;
import com.xrosstools.xunit.impl.ProcessorEnforcer;

public class UnitDef {
	private static boolean enableSpring;
	
	static {
		try {
			Class.forName("org.springframework.context.ApplicationContext");
			enableSpring = true;
		} catch (ClassNotFoundException e) {
			enableSpring = false;
		}
	}
	
	protected UnitDefRepo repo;
	private String name;
	private BehaviorType type;
	private String description;
	private boolean singleton;
	private Unit instance;
	private String className;
	private String moduleName;
	private String referenceName;
	private String key;
	private TaskType taskType;

    private LinkedHashMap<String, String> properties;
	
	public void setUnitDefRepo(UnitDefRepo repo){
		this.repo = repo;
	}
	
	public BehaviorType getType() {
		return type;
	}

	public void setType(BehaviorType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	
	public void setProperties(LinkedHashMap<String, String> properties) {
		this.properties = properties;
	}

	private boolean isEmpty(String str){
		if(str == null || str.trim().length() == 0)
			return true;
		return str.trim().equalsIgnoreCase("default");
	}
	
	protected Unit createDefault(){
	    return new DefaultUnitImpl(this);
	}
	
	protected UnitDefinition createDefinition() {
	    UnitDefinition ud = new UnitDefinition();
	    
	    ud.setName(name);
	    ud.setType(type);
	    ud.setDescription(description);
	    ud.setSingleton(singleton);
	    ud.setClassName(className);
	    ud.setKey(key);
	    ud.setTaskType(taskType);

	    return ud;
	}

	protected Unit createInstance() throws Exception{
	    Unit unit = null;
		if(!isEmpty(className)){
			if(enableSpring)
				unit = XunitSpring.getBean(className);

			if(unit == null)
				unit = (Unit)Class.forName(className).getDeclaredConstructor().newInstance();

		    return setAware(unit);
		}
		
		if(!isEmpty(referenceName))
			return createReferenceInsance();
		
		return setAware(createDefault());
	}
	
	private Unit setAware(Unit unit) {
        if(unit instanceof ApplicationPropertiesAware){
            ApplicationPropertiesAware aware = (ApplicationPropertiesAware)unit;
            // Make a copy
            aware.setApplicationProperties(new LinkedHashMap<String, String>(repo.getApplicationProperties()));
        }
        
        if(unit instanceof UnitPropertiesAware){
            UnitPropertiesAware aware = (UnitPropertiesAware)unit;
            // Make a copy
            aware.setUnitProperties(new LinkedHashMap<String, String>(properties));
        }
        
        if(unit instanceof UnitDefinitionAware){
            UnitDefinitionAware aware = (UnitDefinitionAware)unit;
            aware.setUnitDefinition(createDefinition());
        }

        return unit;
	}
	
	protected Unit createReferenceInsance() throws Exception{
	    if(moduleName == null || moduleName.trim().length() == 0)
	        return repo.getUnit(referenceName);
	    else{
	        return XunitFactory.load(moduleName).getUnit(referenceName);
	    }
	}
	
	private Unit enforce(Unit newInstance) {
	    if(type == BehaviorType.processor && newInstance instanceof Converter)
	        return new ProcessorEnforcer(newInstance);
	    
	    if(type == BehaviorType.converter && newInstance instanceof Processor)
	        return new ConverterEnforcer(newInstance);
	    
	    return newInstance;
	}

	/**
	 * if it is singleton, we should return the only instance
	 * @return
	 * @throws Exception 
	 */
	public final Unit getInstance() throws Exception{
		if(singleton && instance != null)
			return instance;

		if(singleton && instance == null) {
		    synchronized(this) {
		        if(instance != null)
		            return instance;

    			return instance = enforce(createInstance());
		    }
		}
		
		return enforce(createInstance());
	}
	
	/**
	 * Just a helper method to let subclass create child unit.
	 * The parent  
	 */
	public static Unit getInstance(UnitDef def) throws Exception{
		return  def == null ? null : def.getInstance();
	}	
}
