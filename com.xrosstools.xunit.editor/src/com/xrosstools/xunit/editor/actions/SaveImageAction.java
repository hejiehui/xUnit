package com.xrosstools.xunit.editor.actions;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.ui.IWorkbenchPart;

public class SaveImageAction extends WorkbenchPartAction implements UnitActionConstants {
    private ScrollingGraphicalViewer viewer;
    public static final String ID = ID_PREFIX + SAVE_IMPAGE;
    public SaveImageAction(IWorkbenchPart part) {
        super(part);
        setId(ID);
    }

    public void run() {
        saveOutlinePicture();
    }

    public void setViewer(ScrollingGraphicalViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * This copied from web
     * http://blog.csdn.net/moneyice/article/details/1524371
     * @param viewer
     */
    private void saveOutlinePicture() {
        LayerManager layerManager = (LayerManager) viewer.getEditPartRegistry().get(LayerManager.ID);
        // save image using swt
        // get root figure
           // 获取显示在editor中的背景层和打印层
        IFigure backgroundLayer = layerManager.getLayer(LayerConstants.GRID_LAYER);

        IFigure contentLayer = layerManager.getLayer(LayerConstants.PRINTABLE_LAYERS);

        // create image from root figure
        Image img = new Image(null, contentLayer.getSize().width, contentLayer.getSize().height);
        GC gc = new GC(img);
        Graphics graphics = new SWTGraphics(gc);
        graphics.translate(contentLayer.getBounds().getLocation());
             // 传送显示图层
        backgroundLayer.paint(graphics);
        contentLayer.paint(graphics);
        graphics.dispose();
        gc.dispose();

        // save image to file
        ImageLoader il = new ImageLoader();
        il.data = new ImageData[] { img.getImageData() };

            //保存地址和保存图片的类型
        il.save("c:/test.jpg", SWT.IMAGE_JPEG);
    }

    @Override
    protected boolean calculateEnabled() {
        return true;
    }
}
