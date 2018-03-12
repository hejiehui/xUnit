package com.xrosstools.xunit.idea.editor.util;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.*;

public class PropertyTableModel extends AbstractTableModel {
    private static final String DEFAULT = "default";
    private IPropertySource source;
    private class TableRow {
        boolean isCategory;
        String categoryName;
        String propertyName;
        IPropertyDescriptor propertyDescriptor;
    }

    private List<TableRow> internalRows = new ArrayList<>();

    public PropertyTableModel() {
    }

    public PropertyTableModel(IPropertySource source) {
        this.source = source;

        Map<String, List<TableRow>> rowByCategory = new HashMap<>();

        for (IPropertyDescriptor d: source.getPropertyDescriptors()) {
            String catName = d.getCategory();
            if(catName == null)
                catName = DEFAULT;
            List<TableRow> rows = rowByCategory.get(catName);
            if(rows == null) {
                rows = new ArrayList<>();
                TableRow header = new TableRow();
                header.isCategory = true;
                header.categoryName = d.getCategory();
                rows.add(header);
                rowByCategory.put(catName, rows);
            }

            TableRow row = new TableRow();
            row.propertyName = d.getPropertyName();
            row.propertyDescriptor = d;
            rows.add(row);
        }

        if(rowByCategory.size() == 1) {
            internalRows.addAll(rowByCategory.get(DEFAULT));
            // Remove header
            internalRows.remove(0);
        }
        else {
            Set<String> categories = new TreeSet<>(rowByCategory.keySet());
            for (String catName : categories) {
                internalRows.addAll(rowByCategory.get(catName));
            }
        }
    }

    public IPropertyDescriptor getDescriptor(int row) {
        return internalRows.get(row).propertyDescriptor;
    }

    public String getDisplayText(int rowIndex, int columnIndex, Object value) {
        if(columnIndex == 1 && getDescriptor(rowIndex) instanceof ComboBoxPropertyDescriptor)
            return ((ComboBoxPropertyDescriptor)getDescriptor(rowIndex)).getValues()[(int)value];
        else
            return value == null ? "": value.toString();
    }

    @Override
    public int getRowCount() {
        return source == null ? 0 : internalRows.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnIndex == 0 ? "Property" : "Value";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return IPropertyDescriptor.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return internalRows.get(rowIndex).isCategory == false && columnIndex == 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TableRow row = internalRows.get(rowIndex);

        if(columnIndex == 0) {
            return row.isCategory ? row.categoryName : "        " + row.propertyName;
        } else {
            return isCellEditable(rowIndex, columnIndex) ?
                    source.getPropertyValue(internalRows.get(rowIndex).propertyName) :
                    null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        source.setPropertyValue(internalRows.get(rowIndex).propertyName, aValue);
    }
}
