package com.xrosstools.xunit.sample;

import com.xrosstools.xunit.Context;

public class LotteryDrawContext implements Context {
	public String name;
	public double quantity;
	public String operator;
	
	public LotteryDrawContext(String name, double quantity, String operator) {
		this.name = name;
		this.quantity = quantity;
		this.operator = operator;
	}
}
