package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xunit.idea.editor.commands.AssignClassCommand;
import com.xrosstools.xunit.idea.editor.model.UnitNode;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

public class AssignClassNameAction extends Action implements UnitActionConstants {
	private Project project;
	private BaseNodePart nodePart;

	public AssignClassNameAction(Project project, BaseNodePart nodePart) {
		setText(ASSIGN_CLASS);
		this.project = project;
		this.nodePart = nodePart;
	}

    public Command createCommand() {
        UnitNode node = (UnitNode)nodePart.getModel();
        String className = node.getImplClassName();
        TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project).createAllProjectScopeChooser(className == null ? "" : className);
        chooser.showDialog();
        PsiClass selected = chooser.getSelected();
        if(selected == null)
            return null;

        String qName = selected.getQualifiedName();

        return new AssignClassCommand((UnitNode)nodePart.getModel(), qName);
	}
}
