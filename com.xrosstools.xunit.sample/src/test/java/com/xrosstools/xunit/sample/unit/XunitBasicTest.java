package com.xrosstools.xunit.sample.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xunit.Context;
import com.xrosstools.xunit.XunitFactory;

public class XunitBasicTest {
	private XunitFactory f; 
	@Before
	public void setup() throws Exception {
		f = XunitFactory.load("unit_test.xunit");	
	}
	
	@Test
	public void testProcessor() throws Exception {
		TextContext ctx = new TextContext("my process test");
		
		f.getProcessor("Processor test").process(ctx);
		
		assertEquals("Processed: my process test", ctx.getValue());
	}

	@Test
	public void testConverter() throws Exception {
		Context ctx = f.getConverter("Converter test").convert(new TextContext("123456789"));
		assertEquals(9, ((IntegerContext)ctx).getValue().intValue());
	}

	@Test
	public void testChain() throws Exception {
		String text = "my process test";
		TextContext ctx = new TextContext(text);
		
		f.getProcessor("Chain test").process(ctx);
		assertEquals(("Processed: " + text).toLowerCase(), ctx.getValue());
	}
	
	@Test
	public void testBiBranch() throws Exception {
		String text = "my process test";
		TextContext ctx = new TextContext(text);
		
		f.getProcessor("Bibranch test").process(ctx);
		assertEquals(text.toUpperCase(), ctx.getValue());
		
		text = "my proce";
		ctx = new TextContext(text);
		
		f.getProcessor("Bibranch test").process(ctx);
		assertEquals(text.toLowerCase(), ctx.getValue());
	}

	@Test
	public void testBranch() throws Exception {
		String text = "abcd";
		TextContext ctx = new TextContext(text);
		
		text = "abcdeabcd";
		ctx.setValue(text);
		f.getProcessor("Branch test").process(ctx);
		assertEquals("Processed: " + text, ctx.getValue());
		
		text = "abcdeabcde";
		ctx.setValue(text);
		f.getProcessor("Branch test").process(ctx);
		assertEquals(text.toUpperCase(), ctx.getValue());
		
		text = "abcdeabcdeabcdeabcdeaABCDEFRE";
		ctx.setValue(text);
		f.getProcessor("Branch test").process(ctx);
		assertEquals(text.toLowerCase(), ctx.getValue());
		
		text = null;
		ctx.setValue(text);
		f.getProcessor("Branch test").process(ctx);
		assertEquals("Processed: null", ctx.getValue());		
	}
	
	@Test
	public void testWhileLoop() throws Exception {
		String text = "abcddfgbehajkakkk";
		TextContext ctx = new TextContext(text);
		
		ctx.setValue(text);
		f.getProcessor("While loop test").process(ctx);
		assertEquals("behajkakkk", ctx.getValue());
	
	}
	
	@Test
	public void testDoWhileLoop() throws Exception {
		String text = "abcddfgbehajkakkk";
		TextContext ctx = new TextContext(text);
		
		ctx.setValue(text);
		f.getProcessor("Do-while loop test").process(ctx);
		assertEquals("behajkakkk", ctx.getValue());
	
	}
	
	@Test
	public void testDecorator() throws Exception {
		String text = "abcd";
		TextContext ctx = new TextContext(text);
		
		ctx.setValue(text);
		IntegerContext ic = (IntegerContext)f.getConverter("Decorator test").convert(ctx);
		assertEquals(text.length() + 100 + 3, ic.getValue().intValue());
	
	}

	@Test
	public void testAdapter() throws Exception {
		String text = "abcd";
		TextContext ctx = new TextContext(text);
		
		ctx.setValue(text);
		IntegerContext ic = (IntegerContext)f.getConverter("Adapter test").convert(ctx);
		assertEquals("Processed: ".length() + text.length(), ic.getValue().intValue());
	}

}
