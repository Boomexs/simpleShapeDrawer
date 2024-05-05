package com.boomexs.shapeseditor;

import java.io.Serializable;

public class ECircle extends Entity implements Serializable {
    private static final long serialVersionUID = 10L;
    public double Radius, x1, y1;

    public ECircle(short r, short g, short b, double x, double y, double radius) {
        super.r = r;
        super.g = g;
        super.b = b;
        this.x1 = x;
        this.y1 = y;
        this.Radius = radius;
    }
}
