package com.xrosstools.xunit.sample.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;
import com.xrosstools.xunit.XunitSpring;

/**
 * This case shows how to enable spring by declare a bean
 */
@Configuration
@ComponentScan
@PropertySource("user.properties")
public class AppConfig2 {
    @Bean
    XunitSpring createSpringBeanFactory() {
        return new XunitSpring();
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig2.class);
        
        XunitFactory f = XunitFactory.load("spring_support.xunit");

        UserContext ctx = new UserContext();
        ctx.setName("tom");
        
        Processor p = f.getProcessor("test");
        p.process(ctx);
        System.out.println(ctx.getPromotion());
        
        ctx.setName("Jerry He");
        p.process(ctx);
        System.out.println(ctx.getPromotion());
        System.out.println(ctx.getComments());
	}
}
