package com.xrosstools.xunit.sample;

import java.util.Map;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.Converter;
import com.xrosstools.xunit.UnitPropertiesAware;

public class Calculator implements Converter, UnitPropertiesAware {
	private double quantity;
	
	@Override
	public Context convert(Context contxt) {
		LotteryDrawContext ctx = (LotteryDrawContext)contxt;
		
		double value = ctx.quantity;
		switch(ctx.operator){
			case "+": value+=quantity; break;
			case "-": value-=quantity; break;
			case "*": value*=quantity; break;
			case "/": value/=quantity; break;
		}
		
		System.out.println(String.format("%g %s %g = %g", ctx.quantity, ctx.operator, quantity, value));
		ctx.quantity = value;
		
		return ctx;
	}

	@Override
	public void setUnitProperties(Map<String, String> arg0) {
		quantity = Double.parseDouble(arg0.get("quantity"));
	}
}
