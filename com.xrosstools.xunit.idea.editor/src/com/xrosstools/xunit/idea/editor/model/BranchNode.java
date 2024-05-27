package com.xrosstools.xunit.idea.editor.model;

public class BranchNode extends CompositeUnitNode {
    private LocatorNode locator;
    private UnitNodePanel unitsPanel = new UnitNodePanel(this);
    private EndPointNode endPoint = new EndPointNode();

    public BranchNode(boolean empty){
        super("a branch", StructureType.branch);
        if(empty)
            return;

        init();
    }

    public BranchNode(){
        this(false);
    }

    public BranchNode(LocatorNode locator, UnitNode unit){
        this(true);
        setLocator(locator);
        addUnit("key 1", null, unit);
    }

    private void init(){
        setLocator(new LocatorNode());
        for(int i = 0; i < 3; i++)
            addUnit("key" + (i + 1), null, createSampleNode("node " + (i + 1)));
    }

    public String getDefaultImplName(){
        return DEFAULT_BRANCH_IMPL;
    }

    protected String getCategory(String id) {
        return id == PROP_CLASS || id == PROP_REFERENCE ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public LocatorNode getLocator() {
        return locator;
    }

    public void addUnit(String key, String label, UnitNode unit){
        unitsPanel.add(unit);
        unit.setInputKey(key);
        unit.setInputLabel(label);
    }

    public void setLocator(LocatorNode locator) {
        LocatorNode oldLocator = this.locator;
        this.locator = locator;
        String key;
        for(UnitNode node: unitsPanel.getAll()){
            key = MSG_NOT_SPECIFIED;
            if(oldLocator != null){
                key = node.getInputLabel();
                node.removeAllInputs();
            }
            UnitNodeConnection.linkStart(locator, node, key);
        }
        firePropertyChange(PROP_NODE);
    }

    public void checkLink(){
        UnitNodeConnection.remove(locator, endPoint);
        if(unitsPanel.size() == 0)
            UnitNodeConnection.linkStart(locator, endPoint, MSG_EMPTY);
    }

    public UnitNode getStartNode(){
        return getLocator();
    }

    public UnitNodeContainer getContainerNode(){
        return unitsPanel;
    }

    public UnitNode getEndNode(){
        return endPoint;
    }

    public void unitAdded(int index, UnitNode unit) {
        unit.removeAllConnections();
        UnitNodeConnection.linkEnd(unit, endPoint);
        UnitNodeConnection.linkStart(locator, unit, MSG_NOT_SPECIFIED);
        checkLink();
        firePropertyChange(PROP_NODE);
    }

    public void unitRemoved(UnitNode unit) {
        unit.removeAllConnections();
    }
}
