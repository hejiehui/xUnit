package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodePartFactory {
    private UnitNodeHelper helper;
    private EditContext editContext;

    public UnitNodePartFactory(EditContext editContext) {
        this.editContext = editContext;
    }

    public EditPart createEditPart(EditPart parent, Object model) {
        EditPart part = null;
        if(model == null)
            return part;

        if (model instanceof UnitNodeDiagram){
            if(helper == null)
                helper = new UnitNodeHelper((UnitNodeDiagram)model);
            part = new UnitNodeDiagramPart();
        }
        else if (model instanceof CompositeUnitNode)
            part = new CompositeUnitNodePart();
        else if (model instanceof UnitNodePanel)
            part = new UnitNodeContainerPart();
        else if (model instanceof IconNode)
            part = new IconNodePart();

        else if (model instanceof UnitNode)
            part = new UnitNodePart();

        else if (model instanceof UnitNodeConnection)
            part = new UnitNodeConnectionPart();

        if(model instanceof UnitNode){
            ((UnitNode)model).setHelper(helper);
            ((UnitNode)model).setPart(part);
        }

        part.setEditPartFactory(this);
        part.setModel(model);
        part.setParent(parent);
        part.setContext(editContext);
        editContext.add(part, model);

        return part;
    }
}
