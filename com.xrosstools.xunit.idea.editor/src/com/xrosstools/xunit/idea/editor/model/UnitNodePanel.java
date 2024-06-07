package com.xrosstools.xunit.idea.editor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnitNodePanel implements UnitNodeContainer {
    private CompositeUnitNode parent;
    private List<UnitNode> units = new ArrayList<UnitNode>();
    private int UNLIMITED_SIZE = -1;
    private int fixedSize = UNLIMITED_SIZE;
    private boolean vertical = true;

    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public UnitNodePanel(CompositeUnitNode parent){
        this(parent, true);
    }

    public UnitNodePanel(CompositeUnitNode parent, boolean vertical){
        this.parent = parent;
        this.vertical = vertical;
    }

    public UnitNodePanel(CompositeUnitNode parent, int fixedSize){
        this(parent, true);
        this.fixedSize = fixedSize;
        init();
    }

    private void init(){
        if(fixedSize == UNLIMITED_SIZE)
            return;

        for(int i = 0; i< fixedSize; i++)
            units.add(null);
    }

    private void firePropertyChange(String propertyName){
        listeners.firePropertyChange(propertyName, null, null);
    }

    public boolean isVertical(){
        return vertical;
    }

    public int size(){
        int i = 0;
        for(UnitNode unit:units)
            i += (unit == null ? 0 : 1);
        return i;
    }

    public int indexOf(UnitNode unit){
        return units.indexOf(unit);
    }

    public boolean contains(UnitNode unit){
        return units.contains(unit);
    }

    public UnitNode get(int index){
        return units.get(index);
    }

    public List<UnitNode> getAll(){
        List<UnitNode> tmpUnits = new ArrayList<UnitNode>();
        for(UnitNode unit:units)
            if(unit != null)
                tmpUnits.add(unit);

        return tmpUnits;
    }

    public boolean isFixedSize(){
        return fixedSize != UNLIMITED_SIZE;
    }

    public int getFixedSize(){
        return fixedSize;
    }

    public boolean checkDropAllowed(int index){
        if(fixedSize == UNLIMITED_SIZE)
            return true;

        return fixedSize != size();
    }

    public CompositeUnitNode getParent() {
        return parent;
    }

    public boolean add(int index, UnitNode unit) {
        if(!checkDropAllowed(index))
            return false;

        if(isFixedSize()){
            if(index < fixedSize && units.get(index) == null)
                set(index, unit);
            else if(index == fixedSize && units.get(0) != null)
                set(1, unit);
            else if(index == 1 && units.get(0) == null)
                set(0, unit);
            else
                return false;
        }
        else {
            unit.removeAllConnections();
            units.add(index, unit);
            parent.unitAdded(index, unit);
            firePropertyChange(PROP_NODE);
        }

        return true;
    }

    public boolean add(UnitNode unit){
        if(!checkDropAllowed(size()))
            return false;

        if(!isFixedSize())
            return add(size(), unit);

        if(units.get(0) == null)
            set(0, unit);
        else
            set(1, unit);

        return true;
    }

    public void set(int index, UnitNode unit){
        if(!checkDropAllowed(index))
            return;

        if(unit != null)
            unit.removeAllConnections();

        units.set(index, unit);
        parent.unitSet(index, unit);
        firePropertyChange(PROP_NODE);
    }

    public void remove(UnitNode unit) {
        if(!units.contains(unit))
            return;

        if(isFixedSize())
            units.set(indexOf(unit), null);
        else
            units.remove(unit);

        unit.removeAllConnections();
        parent.unitRemoved(unit);
        firePropertyChange(PROP_NODE);
    }

    public void move(int newIndex, UnitNode unit){
        // For non fixed size or full fixed size
        if(!isFixedSize()){
            // No connection change
            units.remove(unit);
            units.add(newIndex, unit);
        }else{
            // just switch. should only be the bi-branch case
            Collections.reverse(units);
        }

        parent.unitMoved(unit);
        firePropertyChange(PROP_NODE);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
}

