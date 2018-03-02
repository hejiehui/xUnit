package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.*;

import java.awt.*;

public class UnitNodeDiagramFigure extends Figure implements UnitConstants {
    public UnitNodeDiagramFigure() {
        getInsets().set(V_NODE_SPACE, H_NODE_SPACE, V_NODE_SPACE, H_NODE_SPACE);
        setLayout(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT, 100));
        setLocation(H_NODE_SPACE, V_NODE_SPACE);
    }

    public Figure getContentPane(){
        return this;
    }

    public void paintComponent(Graphics graphics) {
        paintInsertionFeedback(graphics);
    }

    // Do not show selection frame
    public void paintSelection(Graphics graphics) {}

}
