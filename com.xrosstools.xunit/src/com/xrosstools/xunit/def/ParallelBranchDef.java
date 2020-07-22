package com.xrosstools.xunit.def;

import java.util.ArrayList;
import java.util.List;

import com.xrosstools.xunit.Dispatcher;
import com.xrosstools.xunit.ParallelBranch;
import com.xrosstools.xunit.Unit;
import com.xrosstools.xunit.impl.ParallelBranchImpl;

public class ParallelBranchDef extends UnitDef{
    private UnitDef dispatcherDef;
    
    public UnitDef getDispatcherDef() {
        return dispatcherDef;
    }

    public void setDispatcherDef(UnitDef dispatcherDef) {
        this.dispatcherDef = dispatcherDef;
    }

    private List<UnitDef> branchUnitDefs = new ArrayList<UnitDef>();
    
    public List<UnitDef> getBranchUnitDefs() {
        return branchUnitDefs;
    }
    
    public void addBranchUnitDef(UnitDef branchUnitDef) {
        branchUnitDefs.add(branchUnitDef);
    }

    protected Unit createDefault(){
        return new ParallelBranchImpl();
    }

    protected Unit createInstance() throws Exception{
        ParallelBranch parallelBranch = (ParallelBranch)super.createInstance();
        parallelBranch.setDispatcher(((Dispatcher)getInstance(dispatcherDef)));
        
        for(UnitDef def : branchUnitDefs){
            Unit unit = getInstance(def);
            parallelBranch.add(def.getKey(), def.getTaskType(), unit);
        }
        
        parallelBranch.validate();
        return parallelBranch;
    }       
}
