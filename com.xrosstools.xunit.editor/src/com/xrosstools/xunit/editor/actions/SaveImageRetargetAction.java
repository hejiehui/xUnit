package com.xrosstools.xunit.editor.actions;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.LabelRetargetAction;

public class SaveImageRetargetAction extends LabelRetargetAction {

    /**
     * Constructs a new UndoRetargetAction with the default ID, label and image.
     */
    public SaveImageRetargetAction() {
        super(SaveImageAction.ID, UnitActionConstants.SAVE_IMPAGE);
        ISharedImages sharedImages = PlatformUI.getWorkbench()
                .getSharedImages();
        setImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));
        setDisabledImageDescriptor(sharedImages
                .getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT_DISABLED));
    }

}
