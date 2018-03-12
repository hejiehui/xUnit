package com.xrosstools.xunit.idea.editor.actions;

import com.xrosstools.xunit.idea.editor.model.UnitNodeDiagram;

public class SaveImageAction {
//        extends WorkbenchPartAction implements UnitActionConstants {
////    private ScrollingGraphicalViewer viewer;
//    private UnitNodeDiagram diagram;
//    public static final String ID = ID_PREFIX + SAVE_IMPAGE;
//    public SaveImageAction() {
//        setText(ID);
//    }
//
//    public void run() {
//        saveOutlinePicture();
//    }
//
//    public void setData(ScrollingGraphicalViewer viewer, UnitNodeDiagram diagram) {
//        this.viewer = viewer;
//        this.diagram = diagram;
//    }
//
//    /**
//     * This copied from web
//     * http://blog.csdn.net/moneyice/article/details/1524371
//     * @param viewer
//     */
//    private void saveOutlinePicture() {
//        LayerManager layerManager = (LayerManager) viewer.getEditPartRegistry().get(LayerManager.ID);
//        // save image using swt
//        // get root figure
//           // 获取显示在editor中的背景层和打印层
//        IFigure backgroundLayer = layerManager.getLayer(LayerConstants.GRID_LAYER);
//
//        IFigure contentLayer = layerManager.getLayer(LayerConstants.PRINTABLE_LAYERS);
//
//        // create image from root figure
//        Image img = new Image(null, contentLayer.getSize().width, contentLayer.getSize().height);
//        GC gc = new GC(img);
//        Graphics graphics = new SWTGraphics(gc);
//        graphics.translate(contentLayer.getBounds().getLocation());
//             // 传送显示图层
//        backgroundLayer.paint(graphics);
//        contentLayer.paint(graphics);
//        graphics.dispose();
//        gc.dispose();
//
//        // save image to file
//        ImageLoader il = new ImageLoader();
//        il.data = new ImageData[] { img.getImageData() };
//
//        String name = diagram.getFilePath().getName() + ".jpg";
//        IFile imgFile = diagram.getFilePath().getParent().getFile(new Path(name));
//        String path = imgFile.getLocation().toOSString();
//
//        il.save(path, SWT.IMAGE_JPEG);
//        try {
//            imgFile.refreshLocal(IResource.DEPTH_ZERO,null);
//        } catch (CoreException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected boolean calculateEnabled() {
//        return true;
//    }
}
