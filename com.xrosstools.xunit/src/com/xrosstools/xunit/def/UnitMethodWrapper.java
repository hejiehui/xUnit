package com.xrosstools.xunit.def;

import java.lang.reflect.Method;

import com.xrosstools.xunit.BehaviorType;
import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.Locator;
import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.Validator;

public class UnitMethodWrapper implements Unit {
	private static final Class<?>[] CONTEXT_PARAMS = new Class[] {Context.class};
	protected Class<?>[] parameterClasses;

	protected Object instance;
	protected Method method;
	
	UnitMethodWrapper(Class<?>[] parameterClasses) {
		this.parameterClasses = parameterClasses;
	}

	Object invoke(Object...params) {
		try {
			return method.invoke(instance, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static Unit create(BehaviorType type, Object instance, String methodName) {
		UnitMethodWrapper wrapper = null;

		switch (type) {
		case processor:
			wrapper = new ProcessorMethodWraper();
			break;
		case converter:
			wrapper = new ConverterMethodWraper();
			break;
		case validator:
			wrapper = new ValidatorMethodWraper();
			break;
		case locator:
			wrapper = new LocatorMethodWraper();
			break;
		default: // dispatcher is not supported
			throw new IllegalArgumentException("dispatcher is not supported for method reference");
		}
		 
		try {
			wrapper.instance = instance;
			wrapper.method = instance.getClass().getDeclaredMethod(methodName, wrapper.parameterClasses);

			//Allow for invoke private method
			wrapper.method.setAccessible(true);  
			return wrapper;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	static class ProcessorMethodWraper extends UnitMethodWrapper implements Processor {
		ProcessorMethodWraper() {
			super(CONTEXT_PARAMS);
		}

		@Override
		public void process(Context ctx) {
			invoke(ctx);
		}
	}

	static class ConverterMethodWraper extends UnitMethodWrapper implements Converter {
		ConverterMethodWraper() {
			super(CONTEXT_PARAMS);
		}

		@Override
		public Context convert(Context ctx) {
			return (Context)invoke(ctx);
		}
	}

	static class ValidatorMethodWraper extends UnitMethodWrapper implements Validator {
		ValidatorMethodWraper() {
			super(CONTEXT_PARAMS);
		}

		@Override
		public boolean validate(Context ctx) {
			return (boolean)invoke(ctx);
		}
	}

	static class LocatorMethodWraper extends UnitMethodWrapper implements Locator {
		private String defaultKey;
		LocatorMethodWraper() {
			super(CONTEXT_PARAMS);
		}

		@Override
		public String locate(Context ctx) {
			return (String)invoke(ctx);
		}

		@Override
		public void setDefaultKey(String key) {
			this.defaultKey = key;
		}

		@Override
		public String getDefaultKey() {
			return defaultKey;
		}
	}
}
