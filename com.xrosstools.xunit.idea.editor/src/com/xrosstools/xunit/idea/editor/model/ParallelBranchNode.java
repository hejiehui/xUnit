package com.xrosstools.xunit.idea.editor.model;

public class ParallelBranchNode extends CompositeUnitNode {
    private DispatcherNode dispatcher;
    private UnitNodePanel unitsPanel = new UnitNodePanel(this);
    private EndPointNode endPoint = new EndPointNode();
    
    public ParallelBranchNode(boolean empty){
        super("a parallel branch", StructureType.parallel_branch);
        if(empty)
            return;
        
        init();
    }
    
    public ParallelBranchNode(){
        this(false);
    }

    public ParallelBranchNode(DispatcherNode dispatcher){
        this(true);
        setDispatcher(dispatcher);
    }
    
    private void init(){
        setDispatcher(new DispatcherNode());
        for(int i = 0; i < 3; i++)
            addUnit("Task " + (i + 1), null, createSampleNode("node " + (i + 1)), TaskType.normal);
    }
    
    public String getDefaultImplName(){
        return DEFAULT_PARALLEL_BRANCH_IMPL;
    }

    protected String getCategory(String id) {
        return id == PROP_CLASS || id == PROP_REFERENCE ?
                CATEGORY_OPTIONAL : CATEGORY_COMMON;
    }

    public DispatcherNode getDispatcher() {
        return dispatcher;
    }
    
    public void addUnit(String key, String label, UnitNode unit, TaskType type){
        unitsPanel.add(unit);
        unit.setInputKey(key);
        unit.setInputLabel(label);
        unit.setTaskType(type);
    }
    
    public void setDispatcher(DispatcherNode dispatcher) {
        DispatcherNode oldDispatcher= this.dispatcher;
        this.dispatcher = dispatcher;
        for(UnitNode node: unitsPanel.getAll()){
            String key = MSG_NOT_SPECIFIED;
            String label = null;
            if(oldDispatcher != null){
                key = node.getInputLabel();
                label = node.getInputLabel();
                node.removeAllInputs();
            }
            UnitNodeConnection.linkStart(dispatcher, node);
            node.setInputKey(key);
            node.setInputLabel(label);
        }
        firePropertyChange(PROP_NODE);
    }
    
    public void checkLink(){
        UnitNodeConnection.remove(dispatcher, endPoint);
        if(unitsPanel.size() == 0)
            UnitNodeConnection.linkStart(dispatcher, endPoint, MSG_EMPTY);
    }
    
    public UnitNode getStartNode(){
        return getDispatcher();
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
        UnitNodeConnection.linkStart(dispatcher, unit).setKey(MSG_NOT_SPECIFIED);
        unit.setTaskType(DEFAULT_TASK_TYPE);
        checkLink();
        firePropertyChange(PROP_NODE);
    }

    public void unitRemoved(UnitNode unit) {
        unit.removeAllConnections();
    }
}
