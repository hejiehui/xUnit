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

    public BranchNode(LocatorNode locator){
        this(true);
        setLocator(locator);
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
        return PROP_CLASS.equals(id) || PROP_REFERENCE.equals(id) ?
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
        for(UnitNode node: unitsPanel.getAll()){
            String key = MSG_NOT_SPECIFIED;
            String label = null;
            if(oldLocator != null){
                key = node.getInputKey();
                label = node.getInputLabel();
                node.removeAllInputs();
            }
            UnitNodeConnection.linkStart(locator, node);
            node.setInputKey(key);
            node.setInputLabel(label);
        }

        checkLink();
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
        UnitNodeConnection.linkStart(locator, unit).setKey(UnitConstants.MSG_NOT_SPECIFIED);
        checkLink();
        firePropertyChange(PROP_NODE);
    }

    public void unitRemoved(UnitNode unit) {
        unit.removeAllConnections();
        checkLink();
        firePropertyChange(PROP_NODE);
    }

    public void reconnect() {
        checkLink();
    }
}
