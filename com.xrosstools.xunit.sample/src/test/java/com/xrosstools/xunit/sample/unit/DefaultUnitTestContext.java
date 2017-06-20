package com.xrosstools.xunit.sample.unit;

import com.xrosstools.xunit.Context;

public class DefaultUnitTestContext implements Context {
    private String locatorFieldPrv;
    private String locatorFieldGetter;
    public String locatorFieldPub;
    
    private boolean validFieldPrv;
    private boolean validFieldGetter;
    public Boolean validFieldPub;
    
    public DefaultUnitTestContext(
            String locatorFieldPrv,
            String locatorFieldGetter,
            String locatorFieldPub,
            boolean validFieldPrv,
            boolean validFieldGetter,
            Boolean validFieldPub
            ) {
        this.locatorFieldPrv = locatorFieldPrv;
        this.locatorFieldGetter = locatorFieldGetter;
        this.locatorFieldPub = locatorFieldPub;
        
        this.validFieldGetter = validFieldGetter;
        this.validFieldPrv = validFieldPrv;
        this.validFieldPub = validFieldPub;
    }
    
    public String getLocatorFieldGetter() {
        return locatorFieldGetter;
    }
    public void setLocatorFieldGetter(String locatorFieldGetter) {
        this.locatorFieldGetter = locatorFieldGetter;
    }
    public boolean isValidFieldGetter() {
        return validFieldGetter;
    }
    public void setValidFieldGetter(boolean validFieldGetter) {
        this.validFieldGetter = validFieldGetter;
    }
    
    public String locate() {
        return locatorFieldPrv;
    }
    
    public Boolean validate() {
        return validFieldPrv;
    }

    public String validateString() {
        return "true";
    }
}
