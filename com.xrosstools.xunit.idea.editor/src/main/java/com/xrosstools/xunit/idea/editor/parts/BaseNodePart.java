package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.figures.AbstractAnchor;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xunit.idea.editor.actions.OpenClassAction;
import com.xrosstools.xunit.idea.editor.figures.TopLevelUnitFigure;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.policies.UnitNodeLayoutPolicy;

import java.beans.PropertyChangeEvent;
import java.util.List;

public abstract class BaseNodePart extends AbstractGraphicalEditPart implements UnitConstants {
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

    @Override
    protected void refreshVisuals() {
        updateName();
    }

    protected void updateName(){
        if(getFigure().getParent() instanceof TopLevelUnitFigure){
            UnitNode unit = getNode();
            TopLevelUnitFigure figure = (TopLevelUnitFigure)getFigure().getParent();
            figure.setName(unit.getName(), unit.getDescription());
        }
    }

    @Override
    public void performAction() {
        UnitNode node = getNode();
        OpenClassAction.openClassOrReference(node.getHelper().getProject(), node);
    }

    @Override
    protected EditPolicy createEditPolicy() {
        return new UnitNodeLayoutPolicy();
    }

    public List<UnitNodeConnection> getModelSourceConnections() {
        return (getNode()).getOutputs();
    }

    public List<UnitNodeConnection> getModelTargetConnections() {
        return (getNode()).getInputs();
    }

    public AbstractAnchor getSourceConnectionAnchor(AbstractConnectionEditPart connectionEditPart) {
        return new UnitAnchor(this.getFigure());
    }

    public AbstractAnchor getTargetConnectionAnchor(AbstractConnectionEditPart connectionEditPart) {
        return new UnitAnchor(this.getFigure());
    }
}
