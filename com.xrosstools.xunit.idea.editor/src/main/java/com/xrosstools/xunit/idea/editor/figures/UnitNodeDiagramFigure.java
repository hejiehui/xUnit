package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.ToolbarLayout;
import com.xrosstools.xunit.idea.editor.model.*;

import java.awt.*;

public class UnitNodeDiagramFigure extends Figure implements UnitConstants {
    public UnitNodeDiagramFigure() {
        getInsets().set(V_NODE_SPACE, H_NODE_SPACE, V_NODE_SPACE, H_NODE_SPACE);
        setLayoutManager(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, 100));
        setLocation(H_NODE_SPACE, V_NODE_SPACE);
        setMinSize(new Dimension(H_NODE_SPACE * 4, V_NODE_SPACE * 3));
    }
}
