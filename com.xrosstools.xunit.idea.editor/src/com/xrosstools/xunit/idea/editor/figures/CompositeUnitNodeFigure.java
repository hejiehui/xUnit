package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.idea.gef.figures.ColorConstants;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.Text;
import com.xrosstools.idea.gef.figures.ToolbarLayout;
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

    public CompositeUnitNodeFigure(boolean vertical, StructureType type) {
        setLayoutManager(getPanelLayout(vertical));

        startPanel = addPanel(this);
        containerPanel = addPanel(this);
        endPanel = addPanel(this);

        initBorder(type);
    }

    private void initBorder(StructureType type){
        this.type = type;
        if(!(type == StructureType.adapter || type == StructureType.decorator))
            return;

        ((ToolbarLayout)getLayoutManager()).setGap(V_NODE_SPACE/2);
        containerPanel.getInsets().set(0, BORDER_WIDTH, 0, BORDER_WIDTH);

        header = new Text();
        header.getInsets().set(BORDER_WIDTH, 0, BORDER_WIDTH, 0);
        header.setForegroundColor(ColorConstants.white);
        startPanel.add(header);

        footer =  new Text();
        footer.getInsets().set(0, 0, BORDER_WIDTH, 0);
        footer.setText(type.name());
        footer.setForegroundColor(borderColor);
        endPanel.add(footer);
    }

    private ToolbarLayout getPanelLayout(boolean vertical){
        ToolbarLayout layout= new ToolbarLayout();
        layout.setHorizontal(!vertical);
        layout.setSpacing(V_NODE_SPACE);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        return layout;
    }

    private Figure addPanel(Figure parent){
        Figure panel = new Figure();
        panel.setLayoutManager(getPanelLayout(false));
        parent.add(panel);
        return panel;
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
        if(!(type == StructureType.adapter || type == StructureType.decorator))
            return;

        Font oldFont = graphics.getFont();
        Color oldColor = graphics.getColor();

        borderColor = type == StructureType.adapter ? ADAPTER_TITLE_COLOR : DECORATOR_TITLE_COLOR;

        graphics.setColor(borderColor);
        graphics.fillRect(getX(), getY(), getWidth(), startPanel.getHeight());
        graphics.setColor(ColorConstants.white);
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());

        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }
}
