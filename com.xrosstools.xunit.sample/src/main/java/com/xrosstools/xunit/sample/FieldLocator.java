package com.xrosstools.xunit.sample;

import java.lang.reflect.Field;
import java.util.Map;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.UnitPropertiesAware;

public class FieldLocator implements Locator, UnitPropertiesAware {
	private String defaultKey;
	private String fieldName;
	private Field field;
	
	@Override
	public String getDefaultKey() {
		return null;
	}

	@Override
	public String locate(Context ctx) {
		try {
			Field[] a = ctx.getClass().getFields();
			field = ctx.getClass().getField(fieldName);
			return field.get(ctx).toString();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return defaultKey;
	}

	@Override
	public void setDefaultKey(String arg0) {
	}

	@Override
	public void setUnitProperties(Map<String, String> arg0) {
		fieldName = arg0.get("field");
	}

}
