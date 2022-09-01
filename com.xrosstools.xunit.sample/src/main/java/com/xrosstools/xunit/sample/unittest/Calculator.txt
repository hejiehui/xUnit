package com.xrosstools.xunit.sample.unittest;

public class Calculator {
	private int quantity = 100;

	public Calculator() {}
	
	public Calculator(int quantity) {
        this.quantity = quantity;
    }

    public int calculate(String operator, int value) {
		switch(operator){
			case "+": value+=quantity; break;
			case "-": value-=quantity; break;
			case "*": value*=quantity; break;
			case "/":
			    if(quantity == 0)
			        throw new IllegalArgumentException();
			    value/=quantity; 
			    break;
		}
		
		return value;
	}
}
