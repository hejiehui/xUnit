package com.xrosstools.xunit.idea.editor.figures;

import sun.swing.SwingUtilities2;

import java.awt.*;

public class Text extends Figure {
    private String text;
    private int defaultHeight = 12;
    private Font font = new Font(null, Font.ROMAN_BASELINE, defaultHeight);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if(text == null)
            return;

        Font oldFont = graphics.getFont();
        Color oldColor = graphics.getColor();

        // paint background
        if(getBackground() != null) {
            graphics.setColor(getBackground());
            Figure p = getParent().getParent();
            graphics.fillRect(p.getX(), p.getY(), p.getWidth(), getParent().getHeight());
            graphics.setColor(Color.white);
            graphics.drawRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
        }

        graphics.setColor(getForeground());

        graphics.setFont(font);

        graphics.drawString(text, getInnerX(), getY() + getHeight() - getInsets().bottom);

        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }

    @Override
    public Dimension getPreferredSize() {
        int height = getMarginHeight();
        int width = getMarginWidth();

        height += font != null ? font.getSize() : defaultHeight;

        if(text == null)
            return new Dimension(width, height);

        width += font == null ? defaultHeight * text.length() : SwingUtilities2.stringWidth(getRootPane(), SwingUtilities2.getFontMetrics(getRootPane(), font), text);

        return new Dimension(width, height);
    }
}
