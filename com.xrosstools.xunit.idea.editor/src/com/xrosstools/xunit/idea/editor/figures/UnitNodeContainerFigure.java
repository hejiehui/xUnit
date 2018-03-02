package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitConstants;

import java.awt.*;

public class UnitNodeContainerFigure extends Figure implements UnitConstants {
    public UnitNodeContainerFigure(boolean vertical, int fixedSize){
        if(vertical)
            getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        else
            getInsets().set(0, BORDER_WIDTH, 0, BORDER_WIDTH);

        setLayout(new ToolbarLayout(!vertical, ToolbarLayout.ALIGN_CENTER, V_NODE_SPACE));
        setOpaque(false);

        for(int i = 0; i < fixedSize; i++){
            Figure panel = new Figure();
            panel.setLayout(new ToolbarLayout(true, ToolbarLayout.ALIGN_CENTER, V_NODE_SPACE));
            add(panel);
        }
    }

    public void paintComponent(Graphics graphics) {
        paintInsertionFeedback(graphics);
    }
}
