package com.xrosstools.xunit.idea.editor.model;

public class AdapterNode extends CompositeUnitNode {
    private UnitNode startNode;
    private UnitNodePanel unitsPanel = new UnitNodePanel(this, 1);
    private UnitNode endNode;

    public AdapterNode(){
        super("an adapter", StructureType.adapter, true);
    }

    public AdapterNode(BehaviorType type){
        this();
        setType(type);
    }

    public String getDefaultImplName(){
        return DEFAULT_ADAPTER_IMPL;
    }

    protected String getCategory(String id) {
        return null;
    }

    public void setUnit(UnitNode unit) {
        unitsPanel.set(INDEX_UNIT, unit);
    }

    public UnitNode getUnit() {
        return  unitsPanel.get(INDEX_UNIT);
    }

    public UnitNode getStartNode(){
        return startNode;
    }
    public UnitNodeContainer getContainerNode(){
        return unitsPanel;
    }
    public UnitNode getEndNode(){
        return endNode;
    }
}
