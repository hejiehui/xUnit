package com.xross.tools.xunit.editor.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;

import com.xross.tools.xunit.editor.Activator;
import com.xross.tools.xunit.editor.model.IconNode;

public class IconNodePart extends BaseNodePart{
	protected IFigure createFigure() {
		IconNode model = (IconNode)getModel();
		return new ImageFigure(Activator.getDefault().getImageRegistry().get(model.getIconId()));
	}
	
    protected void refreshVisuals() {
    	IconNode node = (IconNode)getModel();
    	if(node.isShowTooltip())
	    	getFigure().setToolTip(getToolTipLabel());
    	else
    		figure.setToolTip(null);
    }
}
