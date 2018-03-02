package com.xrosstools.xunit.idea.editor.figures;

public class Label extends Figure {
    private IconFigure icon = new IconFigure();
    private Text text = new Text();

    public Label(String text) {
        this();
        this.text.setText(text);
    }

    public Label() {
        setLayout(new ToolbarLayout(true, ToolbarLayout.ALIGN_CENTER, 5));
        add(icon);
        add(text);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setIcon(String icon) {
        this.icon.setSource(icon);
    }
}