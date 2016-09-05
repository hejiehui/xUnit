package com.xrosstools.xunit.sample;

import java.util.Map;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.UnitPropertiesAware;

public class FieldDisplayer implements Processor, UnitPropertiesAware {
	private String[] fields;
	
	@Override
	public void process(Context ctx) {
		Class<?> clazz = ctx.getClass();
		for(String fieldName: fields) {
			fieldName = fieldName.trim();
			try {
				System.out.println(String.format("%s: %s", fieldName, clazz.getField(fieldName).get(ctx).toString()));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setUnitProperties(Map<String, String> arg0) {
		fields = arg0.get("Fields").split(",");
	}
}
