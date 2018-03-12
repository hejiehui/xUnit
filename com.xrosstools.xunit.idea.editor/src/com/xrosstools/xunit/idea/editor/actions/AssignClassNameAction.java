package com.xrosstools.xunit.idea.editor.actions;


import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.psi.PsiClass;
import com.xrosstools.xunit.idea.editor.parts.BaseNodePart;

public class AssignClassNameAction extends WorkbenchPartAction implements UnitActionConstants {
	private BaseNodePart nodePart;

	public AssignClassNameAction(BaseNodePart nodePart) {
		setText(ASSIGN_CLASS);
		this.nodePart = nodePart;
	}

	public void run() {
		assignClass();
	}

	public void assignClass(){
//		TreeClassChooser chooser = TreeClassChooserFactory.getInstance(project).createProjectScopeChooser("");
//		chooser.showDialog();
//		PsiClass selected = chooser.getSelected();
//		if(selected == null)
//			return;
//
//		String qName = selected.getQualifiedName();
//		System.out.println(qName);

//		IType newType = openDialog();
//		if(newType == null)
//			return;
//
//		setSourceType(newType);
//		if(newType.getFullyQualifiedName().equalsIgnoreCase(getNode().getImplClassName()))
//			return;
//		getViewer().getEditDomain().getCommandStack().execute(new AssignClassCommand((UnitNode)getModel(), newType.getFullyQualifiedName()));
	}
}
