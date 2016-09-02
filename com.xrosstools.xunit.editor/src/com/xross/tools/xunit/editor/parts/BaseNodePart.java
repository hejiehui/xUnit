package com.xross.tools.xunit.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.internal.ui.JavaUIMessages;
import org.eclipse.jdt.internal.ui.dialogs.OpenTypeSelectionDialog;
import org.eclipse.jdt.internal.ui.util.ExceptionHandler;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.xross.tools.xunit.editor.commands.AssignClassCommand;
import com.xross.tools.xunit.editor.figures.TopLevelUnitFigure;
import com.xross.tools.xunit.editor.model.UnitConstants;
import com.xross.tools.xunit.editor.model.UnitNode;
import com.xross.tools.xunit.editor.model.UnitNodeConnection;
import com.xross.tools.xunit.editor.policies.NodeEditPolicy;
import com.xross.tools.xunit.editor.policies.UnitNodeLayoutPolicy;

public abstract class BaseNodePart extends AbstractGraphicalEditPart implements UnitConstants, PropertyChangeListener, NodeEditPart {
	private Label toolTipLabel = new Label();
	
	private IType getSourceType(){
		return getDiagramPart().getSourceType(getNode().getImplClassName());
	}

	private void setSourceType(IType type){
		getDiagramPart().setSourceType(type);
	}
	
	private UnitNodeDiagramPart getDiagramPart(){
		return (UnitNodeDiagramPart)getRoot().getContents();
	}
	
	protected UnitNode getNode(){
		return (UnitNode)getModel();
	}
	
	protected Label getToolTipLabel(){
		toolTipLabel.setText(getToolTip());
		return toolTipLabel;
	}
	
	protected String getToolTip(){
		UnitNode node = getNode();
		StringBuffer sb = new StringBuffer();
		
		append(sb, PROP_NAME, node.getName());
		append(sb, PROP_CLASS, node.getClassName());
		append(sb, PROP_REFERENCE, node.getReferenceName());
		append(sb, PROP_BEHAVIOR_TYPE, node.getType().name());
		append(sb, PROP_DESCRIPTION, node.getDescription());

		return sb.toString();
	}
	
	private void append(StringBuffer sb, String propName, String value){
		if(!getNode().isValid(value))
			return;
		
		if(sb.length() > 0)
			sb.append('\n');
		sb.append(propName).append(SEPARATER).append(value);
	}
	
	protected void addChild(List children, Object node){
    	if(node != null)
    		children.add(node);
    }
    
    protected void updateName(){
    	if(getFigure().getParent() instanceof TopLevelUnitFigure){
	        UnitNode unit = getNode();
	    	TopLevelUnitFigure figure = (TopLevelUnitFigure)getFigure().getParent();
	    	figure.setName(unit.getName(), unit.getDescription());
    	}
    }

	public void activate() {
		super.activate();
		getNode().addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		getNode().removePropertyChangeListener(this);
	}

	public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PROP_NODE))
            refresh();
        else if (evt.getPropertyName().equals(PROP_INPUTS))
            refreshTargetConnections();
        else if (evt.getPropertyName().equals(PROP_OUTPUTS))
            refreshSourceConnections();
		
		updateName();
	}
	
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new UnitNodeLayoutPolicy());
	}

    public void performRequest(Request req) {
        if(req.getType().equals(RequestConstants.REQ_OPEN)){
        	UnitNode node = getNode();
        	if(node.isValid(node.getClassName()))
        		openClass();
        	else if(!node.isValid(node.getReferenceName()))
            	assignClass();
        }
    }
    
    public void openClass(){
    	if(getSourceType() == null)
    		assignClass();

    	if(getSourceType() == null)
    		return;
		
    	try {
			JavaUI.openInEditor(getSourceType(), true, true);
		} catch (CoreException x) {
			ExceptionHandler.handle(x, JavaUIMessages.OpenTypeAction_errorTitle, JavaUIMessages.OpenTypeAction_errorMessage);
		}
    }
    
    public void assignClass(){
    	IType newType = openDialog();
		if(newType == null)
			return;
		
		setSourceType(newType);
		if(newType.getFullyQualifiedName().equalsIgnoreCase(getNode().getImplClassName()))
			return;
		getViewer().getEditDomain().getCommandStack().execute(new AssignClassCommand((UnitNode)getModel(), newType.getFullyQualifiedName()));
    }
    
    private IType openDialog(){
    	Shell parent = Display.getCurrent().getActiveShell();
    	OpenTypeSelectionDialog dialog= new OpenTypeSelectionDialog(parent, true, PlatformUI.getWorkbench().getProgressService(), null, IJavaSearchConstants.TYPE);
		dialog.setTitle(JavaUIMessages.OpenTypeAction_dialogTitle);
		dialog.setMessage(JavaUIMessages.OpenTypeAction_dialogMessage);
		dialog.setInitialPattern(getNode().getImplClassName(), 2);
		
		int result= dialog.open();
		// if cancel clicked, will not change existing type;
		if (result != IDialogConstants.OK_ID)
			return getSourceType();

		Object[] types= dialog.getResult();
		if (types == null || types.length != 1)
			return null;
		return (IType)types[0];
    }
    
    protected void refreshVisuals() {
        Rectangle rectangle = new Rectangle(new Point(0, 0), new Dimension(-1, -1));
        ((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), rectangle);
    }

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return new UnitAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return new UnitAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return new UnitAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return new UnitAnchor(getFigure());
	}
	
    protected List<UnitNodeConnection> getModelSourceConnections() {
    	return (getNode()).getOutputs();
    }

    protected List<UnitNodeConnection> getModelTargetConnections() {
    	return (getNode()).getInputs();    
    }
    
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		child.getParent().remove(child);
	}
}
