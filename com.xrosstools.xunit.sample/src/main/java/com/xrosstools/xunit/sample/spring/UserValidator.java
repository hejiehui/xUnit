package com.xrosstools.xunit.sample.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Validator;

@Component
public class UserValidator implements Validator {
	@Value("${spring.user.name}")
	String userName;

	@Override
	public boolean validate(Context ctx) {
		UserContext uctx = (UserContext)ctx;
		return userName.equals(uctx.getName());
	}
}
