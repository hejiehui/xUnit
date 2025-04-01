package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.xunit.idea.editor.Activator;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;

public class UnitFigure extends RoundedRectangle implements UnitConstants {
    private Label icon;
    private Text label;

    public UnitFigure() {
        setArcHeight(10);
        setArcWidth(10);
        setLayoutManager(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT,0));
        icon = new Label();
        icon .getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 0, BORDER_WIDTH);
        add(icon);

        label = new Text();
        label.getInsets().set(BORDER_WIDTH, BORDER_WIDTH, 10, BORDER_WIDTH);
        add(label);
    }

    public void setLable(String label) {
        this.label.setText(label);
    }

    public void setIcon(String icon) {
        this.icon.setIcon(Activator.getIcon(icon));
        this.icon.setText(icon);
    }
}
