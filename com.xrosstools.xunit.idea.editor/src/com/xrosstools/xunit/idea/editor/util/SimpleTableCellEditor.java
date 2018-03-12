package com.xrosstools.xunit.idea.editor.util;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class SimpleTableCellEditor implements TableCellEditor {
    private PropertyTableModel model;
    private EventListenerList listenerList = new EventListenerList();
    private ChangeEvent changeEvent = new ChangeEvent(this);
    private JComponent editor;

    public SimpleTableCellEditor(PropertyTableModel model) {
        this.model = model;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return editor = model.getDescriptor(row).getEditor(value);
    }

    @Override
    public Object getCellEditorValue() {
        if(editor instanceof JTextField)
            return ((JTextField)editor).getText();
        return ((ComboBox)editor).getSelectedIndex();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        CellEditorListener listener;
        Object[]listeners = listenerList.getListenerList();
        for(int i = 0; i < listeners.length; i++){
            if(listeners[i]== CellEditorListener.class){
                //之所以是i+1，是因为一个为CellEditorListener.class（Class对象），
                //接着的是一个CellEditorListener的实例
                listener= (CellEditorListener)listeners[i+1];
                //让changeEvent去通知编辑器已经结束编辑
                //在editingStopped方法中，JTable调用getCellEditorValue()取回单元格的值，
                //并且把这个值传递给TableValues(TableModel)的setValueAt()
                listener.editingStopped(changeEvent);
            }
        }
        return true;
    }

    @Override
    public void cancelCellEditing() {
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class,l);
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class,l);
    }
}
