package com.xrosstools.xunit.sample.meituan;

public enum UserTypeEnum {
    NEW_USER_ORDINARY("新用户普通"),
    NEW_USER_GRADE("新用户梯度"),
    OLD_USER_TYPE_1("老用户类型1"),
    OLD_USER_TYPE_2("老用户类型2");
    
    private String label;
    UserTypeEnum(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
