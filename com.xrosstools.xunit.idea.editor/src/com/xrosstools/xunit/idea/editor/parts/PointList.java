package com.xrosstools.xunit.idea.editor.parts;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PointList {
    private List<Point> points = new ArrayList();
    public void addPoint(Point p) {
        points.add(p);
    }

    public int[] getXPoints() {
        int[] x = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            x[i] = points.get(i).x;
        }
        return x;
    }
    public int[] getYPoints() {
        int[] y = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            y[i] = points.get(i).y;
        }
        return y;
    }

    public int getPoints() {
        return points.size();
    }

    public Point get(int index) {
        return points.get(index);
    }

    public Point getLast() {
        return points.get(points.size() -1);
    }
}
