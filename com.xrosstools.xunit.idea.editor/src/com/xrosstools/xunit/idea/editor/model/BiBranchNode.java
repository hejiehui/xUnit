package com.xrosstools.xunit.idea.editor.model;

public class BiBranchNode extends CompositeUnitNode {
    private ValidatorNode validator;
    private UnitNodePanel unitsPanel = new UnitNodePanel(this, 2);
    private EndPointNode endPoint = new EndPointNode();

    private UnitNodeConnection validUnitInput = new UnitNodeConnection();
    private UnitNodeConnection validUnitOutput = new UnitNodeConnection();

    private UnitNodeConnection invalidUnitInput = new UnitNodeConnection();
    private UnitNodeConnection invalidUnitOutput = new UnitNodeConnection();

    protected UnitNodeConnection emptyConnection = new UnitNodeConnection();
    private UnitNodeConnection byPassConnection = new UnitNodeConnection();

    public BiBranchNode(boolean empty){
        super("a bibranch", StructureType.bi_branch);
        emptyConnection.setLabel(MSG_EMPTY);
        byPassConnection.setByPassed(unitsPanel);

        validUnitInput.setFirstHalf(true);
        invalidUnitInput.setFirstHalf(true);

        if(empty)
            return;
        setValidator(new ValidatorNode());
        unitsPanel.add(createSampleNode("valid node"));
        unitsPanel.add(createSampleNode("invalide node"));
    }

    public BiBranchNode(){
        this(false);
    }

    public BiBranchNode(ValidatorNode validatorNode){
        this(true);
        setValidator(validatorNode);
    }

    public String getDefaultImplName(){
        return DEFAULT_BI_BRANCH_IMPL;
    }

    protected String getCategory(String id) {
        return id == PROP_CLASS || id == PROP_REFERENCE ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public ValidatorNode getValidator() {
        return validator;
    }

    public UnitNode getValidUnit() {
        return unitsPanel.get(INDEX_VALID);
    }

    public UnitNode getInvalidUnit() {
        return unitsPanel.get(INDEX_INVALID);
    }

    public void setValidUnit(UnitNode unit) {
        unitsPanel.set(INDEX_VALID, unit);
    }

    public void setInvalidUnit(UnitNode unit) {
        unitsPanel.set(INDEX_INVALID, unit);
    }

    public void setValidator(ValidatorNode validator) {
        this.validator = validator;
        reconnect();
    }

    public void reconnect(){
        UnitNode validUnit = getValidUnit();
        UnitNode invalidUnit = getInvalidUnit();

        removeAllOutputs(validator);
        removeAllInputs(endPoint);
        removeAllConnections(validUnit);
        removeAllConnections(invalidUnit);

        if(validUnit == null && invalidUnit == null){
            linkEmptyConnection();
            firePropertyChange(PROP_NODE);
            return;
        }

        linkUnit(validUnitInput, validUnit, validUnitOutput);
        linkUnit(invalidUnitInput, invalidUnit, invalidUnitOutput);

        if(validUnit == null || invalidUnit == null) {
            // Using loop router to make sure the link bypass the unit area
            linkByPassConnection();
        }

        firePropertyChange(PROP_NODE);
    }

    private void linkUnit(UnitNodeConnection input, UnitNode unit, UnitNodeConnection output) {
        if(unit == null)
            return;

        input.link(validator, unit);
        output.link(unit, endPoint);

        input.setPropName(unit == getValidUnit() ? PROP_VALID_LABEL : PROP_INVALID_LABEL);

    }

    private void linkEmptyConnection() {
        emptyConnection.link(getStartNode(), getEndNode());
    }

    private void linkByPassConnection() {
        byPassConnection.setFirstHalf(getInvalidUnit() == null);
        byPassConnection.link(getStartNode(), getEndNode());
    }

        public UnitNode getStartNode(){
        return getValidator();
    }

    public UnitNodeContainer getContainerNode(){
        return unitsPanel;
    }

    public UnitNode getEndNode(){
        return endPoint;
    }
}
