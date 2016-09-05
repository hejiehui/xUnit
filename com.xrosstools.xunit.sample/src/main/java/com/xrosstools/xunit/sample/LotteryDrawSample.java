package com.xrosstools.xunit.sample;

import com.xrosstools.xunit.Processor;
import com.xrosstools.xunit.XunitFactory;

public class LotteryDrawSample {
	public static void main(String[] args) {
		try {
			XunitFactory f = XunitFactory.load("sample.xunit");

			Processor p = f.getProcessor("Lottery Draw");

			test(p, "Jerry", 100, "+");
			test(p, "Marry", 100, "-");
			test(p, "Tom", 100, "*");
			test(p, "Jimmy", 100, "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void test(Processor p, String name, int quantity, String operation) {
		LotteryDrawContext ctx = new LotteryDrawContext(name, quantity, operation);
		p.process(ctx);
		System.out.println("");
	}
}
