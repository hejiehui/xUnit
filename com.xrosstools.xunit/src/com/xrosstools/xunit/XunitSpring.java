package com.xrosstools.xunit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class XunitSpring implements ApplicationContextAware {
    private static volatile ApplicationContext applicationContext;

    public static void enable(ApplicationContext applicationContext) throws BeansException {
        if (XunitSpring.applicationContext == null) {
            XunitSpring.applicationContext = applicationContext;
        }
    }
  
    public static Unit getBean(String className) throws ClassNotFoundException {
    	if(applicationContext == null)
    		return null;

    	try {
    		return (Unit)applicationContext.getBean(Class.forName(className));
    	}catch(BeansException e) {
    		return null;
    	}
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(XunitSpring.applicationContext == null)
			XunitSpring.applicationContext = applicationContext;
	}  
}