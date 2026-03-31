package com.xrosstools.xunit.idea.editor.model;

public class PostValidationLoopNode extends BaseLoopNode {
    private StartPointNode startPoint = new StartPointNode();
    private UnitNodeConnection byPassConnection = new UnitNodeConnection();
    private UnitNodeConnection unitInput = new UnitNodeConnection();
    private UnitNodeConnection unitOutput = new UnitNodeConnection();

    public PostValidationLoopNode(boolean empty){
        super("do-while loop", StructureType.do_while_loop);
        byPassConnection.setByPassed(unitsPanel);
        byPassConnection.setFirstHalf(true);

        unitInput.setFirstHalf(true);
        unitOutput.setFirstHalf(true);

        if(empty)
            return;

        setUnit(createSampleNode("update sum"));
    }

    public PostValidationLoopNode(){
        this(false);
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
        unitInput.link(startPoint, getUnit());
        unitOutput.link(getUnit(), validator);
    }

    //UnitNodeConnection byPassed = UnitNodeConnection.linkStart(validator, startPoint, unitsPanel);
    protected void linkByPassConnection() {
        byPassConnection.link(validator, startPoint);
        byPassConnection.setPropName(PROP_VALID_LABEL);
    }
}
