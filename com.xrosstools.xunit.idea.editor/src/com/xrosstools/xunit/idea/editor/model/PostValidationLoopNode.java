package com.xrosstools.xunit.idea.editor.model;

public class PostValidationLoopNode extends BaseLoopNode {
    private StartPointNode startPoint = new StartPointNode();

    public PostValidationLoopNode(boolean empty){
        super("do-while loop", StructureType.do_while_loop);
        if(empty)
            return;

        setUnit(createSampleNode("update sum"));
    }

    public PostValidationLoopNode(){
        this(false);
    }

    public PostValidationLoopNode(UnitNode unit){
        this(true);
        setUnit(unit);
    }

    public String getDefaultImplName(){
        return DEFAULT_DO_WHILE_LOOP_IMPL;
    }

    public UnitNode getStartNode(){
        return startPoint;
    }

    public UnitNode getEndNode(){
        return validator;
    }

    protected void linkUnit(){
        UnitNode unit = getUnit();
        UnitNodeConnection.linkStart(validator, startPoint, unitsPanel).setPropName(PROP_VALID_LABEL);
        UnitNodeConnection.linkStart(unit, validator);
        UnitNodeConnection.linkStart(startPoint, unit);
    }
}
