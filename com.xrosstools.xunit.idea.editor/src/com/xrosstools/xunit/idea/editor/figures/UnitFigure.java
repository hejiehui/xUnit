package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.UnitConstants;

import java.awt.*;

public class UnitFigure extends Figure implements UnitConstants {
    private Label icon;
    private Text label;

    public UnitFigure() {
        setLayout(new ToolbarLayout(false, ToolbarLayout.ALIGN_TOPLEFT,0));
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
        this.icon.setText(icon);
        this.icon.setIcon(icon);
    }

    public Figure findFigureAt(int x, int y) {
        return containsPoint(x, y) ? this : null;
    }

    public void paintComponent(Graphics g) {
        g.drawRoundRect(getInnerX(), getInnerY(), getInnerWidth(),getInnerrHeight(), 10, 10);
    }
}
