package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.UnitNodeDiagramPanel;
import com.xrosstools.xunit.idea.editor.figures.*;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.model.UnitNodeConnection;
import com.xrosstools.xunit.idea.editor.util.IPropertySource;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EditPart implements UnitConstants, PropertyChangeListener {
    private EditContext context;
    private Figure figure;
    private Object model;
    private int flags;
    private EditPart parent;
    private List<EditPart> childEditParts = new ArrayList<>();
    private int selected;

    private UnitNodePartFactory factory;

    protected abstract Figure createFigure();
    protected void refreshVisuals() {
    }

    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }
    protected void addChildVisual(EditPart childEditPart, int index) {}

    public final void setEditPartFactory(UnitNodePartFactory factory) {
        this.factory = factory;
    }
    public final void build() {
        getFigure();
        List children = getModelChildren();
        for (int i = 0; i < children.size(); i++) {
            EditPart childEditPart = factory.createEditPart(this, children.get(i));
            childEditParts.add(childEditPart);
            childEditPart.build();
            addChildVisual(childEditPart, i);
        }

        List<Connection> connFigures = new ArrayList<>();
        for(UnitNodeConnection conn: getModelSourceConnections()) {
            EditPart connPart = factory.createEditPart(this, conn);
            Connection connFigure = (Connection)connPart.getFigure();
            connFigure.setParent(getFigure());
            connFigures.add(connFigure);
        }
        getFigure().setConnections(connFigures);

        refreshVisuals();
    }

    public final Figure getFigure() {
        if (figure == null) {
            figure = createFigure();
            figure.setPart(this);
            figure.setRootPane(context.getContentPane());
            refreshVisuals();
        }
        return figure;
    }

    public final Object getModel() {
        return model;
    }

    public final void setModel(Object model) {
        this.model = model;
        if(model instanceof IPropertySource) {
            ((IPropertySource)model).getListeners().addPropertyChangeListener(this);
        }
    }

    public boolean isSelectable() {
        if(model == null || !(model instanceof IPropertySource))
            return false;

        return ((IPropertySource)model).getPropertyDescriptors().length != 0;
    }

    public final EditPart findEditPart(Object model) {
        return context.findEditPart(model);
    }

    public final Figure findFigure(Object model) {
        return context.findFigure(model);
    }

    public final void setParent(EditPart parent) {
        this.parent = parent;
    }

    public final EditPart getParent() {
        return parent;
    }

    public final int getSelected() {
        return selected;
    }

    public EditContext getContext() {
        return context;
    }

    public void setContext(EditContext context) {
        this.context = context;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        updateName();
        refreshVisuals();
        refresh();
    }

    private void updateName(){
        if(getFigure().getParent() instanceof TopLevelUnitFigure){
            UnitNode unit = (UnitNode)getModel();
            TopLevelUnitFigure figure = (TopLevelUnitFigure)getFigure().getParent();
            figure.setName(unit.getName(), unit.getDescription());
        }
    }

    public List<UnitNodeConnection> getModelSourceConnections() {
        return new ArrayList<>();
    }

    public List<UnitNodeConnection> getModelTargetConnections() {
        return new ArrayList<>();
    }

    public void refresh() {
        ((UnitNodeDiagramPanel)context.getContentPane()).refresh();
    }
}
