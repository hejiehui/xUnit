package com.xrosstools.xunit.sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Processor;

@Component
public class BestBuyerProcessor implements Processor {
	@Autowired
	Promotion promotion;

	@Override
	public void process(Context ctx) {
		UserContext user = (UserContext)ctx;
		user.setPromotion(promotion.getType());
		user.setComments(promotion.getText());
	}
}
