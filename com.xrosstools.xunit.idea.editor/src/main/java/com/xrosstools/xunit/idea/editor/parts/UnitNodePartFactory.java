package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xunit.idea.editor.model.*;

public class UnitNodePartFactory implements EditPartFactory {
    private UnitNodeHelper helper;
    public EditPart createEditPart(EditPart parent, Object model) {
        AbstractGraphicalEditPart part = null;
        if(model == null)
            return part;

        if (model instanceof UnitNodeDiagram){
            part = new UnitNodeDiagramPart();
            part.setModel(model);
            if(helper == null)
                helper = new UnitNodeHelper(part);
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
        }

        part.setModel(model);

        return part;
    }
}
