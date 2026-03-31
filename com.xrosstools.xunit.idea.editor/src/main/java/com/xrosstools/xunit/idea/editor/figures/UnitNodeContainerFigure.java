package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.ToolbarLayout;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;

import java.awt.*;

public class UnitNodeContainerFigure extends Figure implements UnitConstants {
    public UnitNodeContainerFigure(boolean vertical, int fixedSize){
        if(vertical)
            getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        else
            getInsets().set(0, BORDER_WIDTH, 0, BORDER_WIDTH);

        setLayoutManager(new ToolbarLayout(!vertical, ToolbarLayout.ALIGN_CENTER, V_NODE_SPACE));
        setOpaque(false);

        for(int i = 0; i < fixedSize; i++){
            Figure panel = new Figure();
            panel.setLayoutManager(new ToolbarLayout(true, ToolbarLayout.ALIGN_CENTER, V_NODE_SPACE));
            add(panel);
        }
    }
}
