package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchClassAction {
//    private UnitNodeContainer container;
//    public SearchClassAction(UnitNodeContainer container) {
//        this.container = container;
//    }

    public List<UnitNode> search(UnitNodeContainer container, String className) {
        if(className == null || className.trim().length() == 0)
            return Collections.emptyList();

        List<UnitNode> nodes =  new ArrayList<>();
        className = className.trim();
        search(nodes, container, className);
        return nodes;
    }

    private void check(List<UnitNode> nodeList, UnitNode u, String className) {
        String name = u.getImplClassName();
        if(name != null && name.contains(className))
            nodeList.add(u);
    }

    private void search(List<UnitNode> nodeList, UnitNodeContainer container, String className) {
        for(UnitNode u: container.getAll()) {
            if(u instanceof PrimaryNode) {
                String name = u.getImplClassName();
                if (name != null && name.contains(className))
                    nodeList.add(u);
            } else {
                if (u instanceof CompositeUnitNode) {
                    CompositeUnitNode comp = (CompositeUnitNode) u;
                    check(nodeList, comp.getStartNode(), className);
                    search(nodeList, comp.getContainerNode(), className);
                    check(nodeList, comp.getEndNode(), className);
                }
            }
        }
    }
}
