package com.xrosstools.xunit.sample.spring;

import org.springframework.stereotype.Component;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

@Component
public class NormalBuyerProcessor implements Processor {
	@Override
	public void process(Context ctx) {
		UserContext user = (UserContext)ctx;
		user.setPromotion("Buy one get one");
		user.setComments("Thanks fo chose xunit");
	}
}