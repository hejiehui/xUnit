package com.xross.tools.xunit;

public interface Decorator extends Adapter {
	/**
	 * Extends this method to provide decoration before decorated unit executed
	 * @param ctx
	 */
	void before(Context ctx);

	/**
	 * Extends this method to provide decoration after decorated unit executed
	 * @param ctx
	 */
	void after(Context ctx);
}