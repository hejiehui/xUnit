package com.xrosstools.xunit.sample;

import com.xrosstools.xunit.Context;

public class LotteryDrawContext implements Context {
	private String name;
	private double quantity;
	private String operator;
	
	public LotteryDrawContext(String name, double quantity, String operator) {
		this.name = name;
		this.quantity = quantity;
		this.operator = operator;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
