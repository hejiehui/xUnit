package com.xrosstools.xunit.editor.model;


public enum StructureType {
	primary, chain, bi_branch, branch, while_loop, do_while_loop, decorator, adapter;
	
	public static String[] names = new String[]{
		"Primary", "Chain", "If else", "Branch", "While loop", "Do while loop", "Decorator", "Adapter"
	};

	public static StructureType getType(int index){
		return StructureType.values()[index];
	}
}
