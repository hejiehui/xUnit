package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.TopLevelUnitFigure;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;

import java.beans.PropertyChangeEvent;
import java.util.List;

public abstract class BaseNodePart extends EditPart{
    protected UnitNode getNode(){
        return (UnitNode)getModel();
    }

    protected String getToolTip(){
        UnitNode node = getNode();
        StringBuffer sb = new StringBuffer();

        append(sb, PROP_NAME, node.getName());
        append(sb, PROP_CLASS, node.getClassName());
        append(sb, PROP_REFERENCE, node.getReferenceName());
        append(sb, PROP_BEHAVIOR_TYPE, node.getType().name());
        append(sb, PROP_DESCRIPTION, node.getDescription());

        return sb.toString();
    }

    private void append(StringBuffer sb, String propName, String value){
        if(!getNode().isValid(value))
            return;

        if(sb.length() > 0)
            sb.append('\n');
        sb.append(propName).append(SEPARATER).append(value);
    }

    protected void addChild(List children, Object node){
        if(node != null)
            children.add(node);
    }

    protected void updateName(){
        if(getFigure().getParent() instanceof TopLevelUnitFigure){
            UnitNode unit = getNode();
            TopLevelUnitFigure figure = (TopLevelUnitFigure)getFigure().getParent();
            figure.setName(unit.getName(), unit.getDescription());
        }
    }
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PROP_NODE))
            refresh();
        else if (evt.getPropertyName().equals(PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(PROP_OUTPUTS))
            refreshSourceConnections();

        updateName();
    }

    public List<UnitNodeConnection> getModelSourceConnections() {
        return (getNode()).getOutputs();
    }

    public List<UnitNodeConnection> getModelTargetConnections() {
        return (getNode()).getInputs();
    }
}
