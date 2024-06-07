package com.xrosstools.xunit.idea.editor.model;

public class PreValidationLoopNode extends BaseLoopNode {
    private EndPointNode endPoint = new EndPointNode();
    private UnitNodeConnection byPassConnection = new UnitNodeConnection();
    private UnitNodeConnection unitInput = new UnitNodeConnection();
    private UnitNodeConnection unitOutput = new UnitNodeConnection();

    public PreValidationLoopNode(boolean empty) {
        super("while loop", StructureType.while_loop);

        byPassConnection.setByPassed(unitsPanel);
        byPassConnection.setFirstHalf(true);

        unitInput.setFirstHalf(true);
        unitOutput.setFirstHalf(true);

        if (empty)
            return;

        setUnit(createSampleNode("update sum"));
    }

    public PreValidationLoopNode() {
        this(false);
    }

    public String getDefaultImplName() {
        return DEFAULT_WHILE_LOOP_IMPL;
    }

    public UnitNode getStartNode() {
        return validator;
    }

    public UnitNode getEndNode() {
        return endPoint;
    }

    protected void linkUnit() {
        unitInput.link(validator, getUnit());
        unitInput.setPropName(PROP_VALID_LABEL);
        unitOutput.link(getUnit(), endPoint);
    }

    //UnitNodeConnection byPassed = UnitNodeConnection.linkStart(validator, endPoint, unitsPanel);
    protected void linkByPassConnection() {
        byPassConnection.link(validator, endPoint);
        byPassConnection.setPropName(PROP_INVALID_LABEL);
    }
}