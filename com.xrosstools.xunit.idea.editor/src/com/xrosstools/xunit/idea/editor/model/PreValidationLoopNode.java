package com.xrosstools.xunit.idea.editor.model;

public class PreValidationLoopNode extends BaseLoopNode {
    private EndPointNode endPoint = new EndPointNode();

    public PreValidationLoopNode(boolean empty){
        super("while loop", StructureType.while_loop);
        if(empty)
            return;

        setUnit(createSampleNode("update sum"));
    }

    public PreValidationLoopNode(){
        this(false);
    }

    public PreValidationLoopNode(UnitNode unit){
        this(true);
        setUnit(unit);
    }

    public String getDefaultImplName(){
        return DEFAULT_WHILE_LOOP_IMPL;
    }

    public UnitNode getStartNode(){
        return validator;
    }

    public UnitNode getEndNode(){
        return endPoint;
    }

    protected void linkUnit(){
        UnitNode unit = getUnit();
        UnitNodeConnection.linkStart(validator, endPoint, unitsPanel).setPropName(PROP_INVALID_LABEL);
        UnitNodeConnection.linkStart(validator, unit).setPropName(PROP_VALID_LABEL);
        UnitNodeConnection.linkStart(unit, endPoint);
    }
}