package com.xrosstools.xunit.idea.editor.model;

import java.util.List;

public class ChainNode extends CompositeUnitNode {
    private StartPointNode startPoint = new StartPointNode();
    private UnitNodePanel unitsPanel = new UnitNodePanel(this, false);
    private EndPointNode endPoint = new EndPointNode();

    public UnitNode getStartNode(){
        return startPoint;
    }
    public UnitNodeContainer getContainerNode(){
        return unitsPanel;
    }
    public UnitNode getEndNode(){
        return endPoint;
    }

    public ChainNode(boolean empty){
        super("a chain", StructureType.chain);
        if(empty){
            reconnect();
            return;
        }
        addUnit(createSampleNode("unit 1"));
        addUnit(createSampleNode("unit 2"));
        addUnit(createSampleNode("unit 3"));
    }

    public ChainNode(){
        this(false);
    }

    public String getDefaultImplName(){
        return DEFAULT_CHAIN_IMPL;
    }

    protected String getCategory(String id) {
        return id == PROP_CLASS || id == PROP_REFERENCE ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public List<UnitNode> getUnits() {
        return unitsPanel.getAll();
    }

    public void addUnit(UnitNode unit) {
        unitsPanel.add(unit);
    }

    public void removeUnit(UnitNode unit) {
        unitsPanel.remove(unit);
    }

    public void reconnect(){
        List<UnitNode> all = unitsPanel.getAll();
        all.add(endPoint);
        removeAllOutputs(startPoint);
        UnitNode prev = startPoint;
        for(UnitNode next: all){
            removeAllConnections(next);
            UnitNodeConnection.linkStart(prev, next);
            prev = next;
        }

        if(unitsPanel.size() == 0)
            endPoint.setInputLabel(MSG_EMPTY);

        firePropertyChange(PROP_NODE);
    }
}