package com.xrosstools.xunit.idea.editor.model;

import java.util.List;

public interface UnitNodeContainer extends UnitConstants {
    boolean isVertical();
    int size();
    int indexOf(UnitNode unit);
    boolean contains(UnitNode unit);

    boolean checkDropAllowed(int index);
    int getFixedSize();
    UnitNode get(int index);
    List<UnitNode> getAll();

    boolean add(int index, UnitNode unit);
    /**
     * If return true, then add success. For bi-branch and loop case.
     * This method must use void add(int index, UnitNode unit); to implements the function
     */
    boolean add(UnitNode unit);
    void remove(UnitNode unit);
    void move(int index, UnitNode unit);
}
