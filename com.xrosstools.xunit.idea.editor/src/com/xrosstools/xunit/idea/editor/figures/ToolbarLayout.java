package com.xrosstools.xunit.idea.editor.figures;

import java.awt.*;

public class ToolbarLayout {//implements LayoutManager {
    /** Constant to specify components to be aligned in the center */
    public static final int ALIGN_CENTER = 0;
    /** Constant to specify components to be aligned on the left/top */
    public static final int ALIGN_TOPLEFT = 1;
    /** Constant to specify components to be aligned on the right/bottom */
    public static final int ALIGN_BOTTOMRIGHT = 2;

    private boolean horizontal;
    private int alignment;
    private int gap;

    public void setGap(int gap) {
        this.gap = gap;
    }

    public ToolbarLayout(boolean horizontal, int alignment, int gap) {
        this.horizontal = horizontal;
        this.alignment = alignment;
        this.gap = gap;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

//    public void addLayoutComponent(String name, Component comp) {
//    }
//
//    public void removeLayoutComponent(Component comp) {
//    }

    public void addLayoutComponent(String name, Figure comp) {
    }

    public void removeLayoutComponent(Figure comp) {
    }

    public Dimension preferredLayoutSize(Figure parent) {
        synchronized (parent) {//.getTreeLock()
            Insets ins = parent.getInsets();
            int count = parent.getComponentCount();
            int width = 0;
            int height = 0;
            for(Figure c: parent.getComponents()) {
                Dimension size = c.getPreferredSize();
                c.setSize(size);
                if (horizontal) {
                    width += size.width;
                    height = Math.max(height, size.height);
                }else {
                    height += size.height;
                    width = Math.max(width, size.width);
                }
            }

            if (horizontal)
                width += gap * (count - 1);
            else
                height += gap * (count - 1);

            width += parent.getMarginWidth();
            height += parent.getMarginHeight();
            parent.setSize(width, height);
            return parent.getSize();
        }
    }

    public Dimension minimumLayoutSize(Figure parent) {
        return preferredLayoutSize(parent);
    }

    public void layoutContainer(Figure parent) {
        synchronized (parent) {//.getTreeLock()
            Point innerLoc = parent.getInnerLocation();
            Dimension innerSize = parent.getInnerSize();
            int px = innerLoc.x;
            int py = innerLoc.y;

            int middle = horizontal ? innerSize.height/2 : innerSize.width/2;
            int nextPos = 0;

            for (Figure c :parent.getComponents()) {
                Dimension size = c.getSize();
                int minorPos = horizontal ? size.height : size.width;

                switch (alignment) {
                    case ALIGN_CENTER:
                        minorPos = (int)(middle - minorPos/2);
                        break;
                    case ALIGN_TOPLEFT:
                        minorPos = 0;
                        break;
                    case ALIGN_BOTTOMRIGHT:
                        minorPos = horizontal ? size.height : size.width;
                        break;
                }
                if (horizontal) {
                    c.setLocation(px + nextPos, py + minorPos);
                    nextPos += gap + size.width;
                } else {
                    c.setLocation(px + minorPos, py + nextPos);
                    nextPos += gap + size.height;
                }

                // Make sure child is layout as early as possible to avoid link screwed up
                c.layout();
            }
        }
    }
}
