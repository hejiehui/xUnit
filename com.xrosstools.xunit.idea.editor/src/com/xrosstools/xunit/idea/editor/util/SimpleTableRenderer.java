package com.xrosstools.xunit.idea.editor.util;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class SimpleTableRenderer extends JLabel implements TableCellRenderer {
    private PropertyTableModel model;

    public SimpleTableRenderer(PropertyTableModel model) {
        this.model = model;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(model.getDisplayText(row, column, value));
        return this;
    }
}
