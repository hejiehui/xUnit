package com.xrosstools.xunit.idea.editor.figures;

import com.xrosstools.xunit.idea.editor.Activator;

import javax.swing.*;
import java.awt.*;

public class IconFigure extends Figure {
    private Icon image;

    public IconFigure() {
        setSource(null);
    }

    public IconFigure(String source) {
        setSource(source);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if(image == null)
            return;

        image.paintIcon(getRootPane(), graphics, getInnerX(), getInnerY());
    }

    // Do not show selection frame
    public void paintSelection(Graphics graphics) {}

    public void setSource(String source) {
        image = source == null ? null: Activator.getIcon(source);
        if (image == null)
            setSize(0,16);
        else {
            setSize(image.getIconWidth(), image.getIconHeight());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null)
            return new Dimension(getMarginWidth(), getMarginHeight());
        else
            return new Dimension(image.getIconWidth() + getMarginWidth(), image.getIconHeight() + getMarginHeight());
    }
}
