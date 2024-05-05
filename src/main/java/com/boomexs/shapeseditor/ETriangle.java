package com.boomexs.shapeseditor;

import java.io.Serializable;

public class ETriangle extends Entity implements Serializable {
    private static final long serialVersionUID = 20L;
    public double x1, y1, x2, y2, x3, y3;

    public ETriangle(short r, short g, short b, double x1, double y1, double x2, double y2, double x3, double y3) {
        super.r = r;
        super.g = g;
        super.b = b;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
}
