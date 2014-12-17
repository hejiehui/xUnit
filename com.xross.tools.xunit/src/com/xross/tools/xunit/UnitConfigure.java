package com.xross.tools.xunit;

import java.util.HashMap;
import java.util.Map;

public class UnitConfigure implements XunitConstants {
	private Map<String, Map<String, String>> categories = new HashMap<String, Map<String, String>>();
	
	void setVale(String key, String value){
		setVale(DEFAULT_CATEGORY, key, value);
	}
	
	void setVale(String category, String key, String value){
		if(!categories.containsKey(category))
			categories.put(category, new HashMap<String, String>());
		categories.get(category).put(key, value);
	}

	public String getValue(String key){
		return getValue(DEFAULT_CATEGORY, key);
	}
	
	public String getValue(String category, String key){
		if(!categories.containsKey(category))
			return null;
		return categories.get(category).get(key);
	}
}
