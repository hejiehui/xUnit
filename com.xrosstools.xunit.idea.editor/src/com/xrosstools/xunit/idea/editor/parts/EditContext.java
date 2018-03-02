package com.xrosstools.xunit.idea.editor.parts;

import com.xrosstools.xunit.idea.editor.figures.Figure;

import javax.swing.*;
import java.util.*;

public class EditContext {
    private JComponent contentPane;
    private List<Trinity> contents = new ArrayList<>();

    public EditContext(JComponent contentPane) {
        this.contentPane = contentPane;
    }

    public JComponent getContentPane() {
        return contentPane;
    }

    public void add(EditPart part, Object model) {
        Trinity trinity = new Trinity();
        trinity.part = part;
        trinity.model = model;
        contents.add(trinity);
    }

    public EditPart findEditPart(Object model) {
        return findContent(model).part;
    }

    public Figure findFigure(Object model) {
        return findContent(model).part.getFigure();
    }

    private Trinity findContent(Object model) {
        for(Trinity trinity: contents){
            if(trinity.model == model)
                return trinity;
        }
        return null;
    }

    private class Trinity {
        Object model;
        EditPart part;
    }
}
