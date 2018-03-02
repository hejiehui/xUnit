package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.model.StructureType;
import com.xrosstools.xunit.idea.editor.model.UnitConstants;

import java.awt.*;

public class CompositeUnitNodeFigure extends Figure implements UnitConstants {
    private StructureType type;
    private Text header;
    private Text footer;
    private Figure startPanel;
    private Figure containerPanel;
    private Figure endPanel;
    private Color borderColor;

    public CompositeUnitNodeFigure(boolean horizontal, StructureType type) {
        setLayout(new ToolbarLayout(horizontal, ToolbarLayout.ALIGN_CENTER, V_NODE_SPACE));

        startPanel = new Figure();
        startPanel.setLayout(new ToolbarLayout(horizontal, ToolbarLayout.ALIGN_CENTER, 0));
        containerPanel = new Figure();
        containerPanel.setLayout(new ToolbarLayout(horizontal, ToolbarLayout.ALIGN_CENTER, 0));
        endPanel = new Figure();
        endPanel.setLayout(new ToolbarLayout(horizontal, ToolbarLayout.ALIGN_CENTER, 0));

        add(startPanel);
        add(containerPanel);
        add(endPanel);

        initBorder(type);
    }

    private void initBorder(StructureType type){
        this.type = type;
        if(!(type == StructureType.adapter || type == StructureType.decorator))
            return;

        getLayout().setGap(V_NODE_SPACE/2);
        containerPanel.getInsets().set(0, BORDER_WIDTH, 0, BORDER_WIDTH);

        borderColor = type == StructureType.adapter ? ADAPTER_TITLE_COLOR : DECORATOR_TITLE_COLOR;
        header = new Text();
        header.getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        header.setForeground(Color.white);
        header.setBackground(borderColor);
        startPanel.add(header);

        footer =  new Text();
        footer.getInsets().set(0, 0, BORDER_WIDTH, 0);
        footer.setText(type.name());
        footer.setForeground(borderColor);
        endPanel.add(footer);
    }

    public Figure getStartPanel() {
        return startPanel;
    }

    public Figure getContainerPanel() {
        return containerPanel;
    }

    public Figure getEndPanel() {
        return endPanel;
    }

    public void setLabel(String label){
        if(header == null)
            return;

        header.setText(label);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if(type == StructureType.adapter || type == StructureType.decorator) {
            Color oldColor = graphics.getColor();

            graphics.setColor(isSelected() ? Color.black : borderColor);
            graphics.drawRect(getX(), getY(), getWidth(), getHeight());

            graphics.setColor(oldColor);
        }
    }
}
