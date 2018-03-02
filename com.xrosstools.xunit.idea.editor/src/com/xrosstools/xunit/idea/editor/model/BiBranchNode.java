package com.xrosstools.xunit.idea.editor.model;

public class BiBranchNode extends CompositeUnitNode {
    private ValidatorNode validator;
    private UnitNodePanel unitsPanel = new UnitNodePanel(this, 2);
    private EndPointNode endPoint = new EndPointNode();

    public BiBranchNode(boolean empty){
        super("a bibranch", StructureType.bi_branch);
        if(empty)
            return;
        setValidator(new ValidatorNode());
        unitsPanel.add(createSampleNode("valid node"));
        unitsPanel.add(createSampleNode("invalide node"));
    }

    public BiBranchNode(){
        this(false);
    }

    public BiBranchNode(ValidatorNode validatorNode, UnitNode validNode){
        this(true);
        setValidator(validatorNode);
        unitsPanel.add(validNode);
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
            UnitNodeConnection.linkStart(validator, endPoint, MSG_EMPTY);
            firePropertyChange(PROP_NODE);
            return;
        }

        UnitNodeConnection.linkStart(validator, validUnit).setPropName(PROP_VALID_LABEL);
        UnitNodeConnection.linkEnd(validUnit, endPoint);

        UnitNodeConnection.linkStart(validator, invalidUnit).setPropName(PROP_INVALID_LABEL);
        UnitNodeConnection.linkEnd(invalidUnit, endPoint);

        if(validUnit != null && invalidUnit != null){
            firePropertyChange(PROP_NODE);
            return;
        }

        // Using loop router to make sure the link not pass the unit area
        UnitNodeConnection.linkStart(validator, endPoint, unitsPanel).setFirstHalf(invalidUnit == null);
        firePropertyChange(PROP_NODE);
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
