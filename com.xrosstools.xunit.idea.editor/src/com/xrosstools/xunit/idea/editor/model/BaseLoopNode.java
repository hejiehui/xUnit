package com.xrosstools.xunit.idea.editor.model;

import java.util.Objects;

public abstract class BaseLoopNode extends CompositeUnitNode {
    protected ValidatorNode validator = new ValidatorNode();
    protected UnitNodePanel unitsPanel = new UnitNodePanel(this, 1);
    protected UnitNodeConnection emptyConnection = new UnitNodeConnection();

    public BaseLoopNode(String name, StructureType structureType){
        super(name, structureType);
        emptyConnection.setLabel(MSG_EMPTY);
    }

    protected abstract void linkUnit();
    protected abstract void linkByPassConnection();

    protected String getCategory(String id) {
        return Objects.equals(id, PROP_CLASS) || Objects.equals(id, PROP_REFERENCE) ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public final void reconnect(){
        UnitNode unit = getUnit();

        removeAllConnections(getStartNode());
        removeAllConnections(getEndNode());
        removeAllConnections(getUnit());

        if(unit == null) {
            linkEmptyConnection();
        } else {
            linkUnit();
            linkByPassConnection();
        }

        firePropertyChange(PROP_NODE);
    }

    public void linkEmptyConnection() {
        emptyConnection.link(getStartNode(), getEndNode());
    }

    public UnitNodeContainer getContainerNode(){
        return unitsPanel;
    }

    public ValidatorNode getValidator() {
        return validator;
    }

    public void setValidator(ValidatorNode validator) {
        this.validator = validator;
        reconnect();
    }

    public void setUnit(UnitNode unit) {
        unitsPanel.set(INDEX_UNIT, unit);
    }

    public UnitNode getUnit() {
        return  unitsPanel.get(INDEX_UNIT);
    }
}
