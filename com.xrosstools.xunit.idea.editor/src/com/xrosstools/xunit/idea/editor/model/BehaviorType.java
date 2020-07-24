package com.xrosstools.xunit.idea.editor.model;

public enum BehaviorType {
    processor, converter, validator, locator, dispatcher;

    public static String[] names = getNames();

    private static String[] getNames(){
        String[] names = new String[]{processor.name(), converter.name()};
        return names;
    }

    public static BehaviorType getType(int index){
        return BehaviorType.values()[index];
    }
}
