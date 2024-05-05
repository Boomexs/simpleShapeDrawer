package com.boomexs.shapeseditor;

import java.io.Serializable;

public class ERectangle extends Entity implements Serializable {
    private static final long serialVersionUID = 30L;
    public double x1, y1, width, height;

    public ERectangle(short r, short g, short b, double x1, double y1, double width, double height) {
        super.r = r;
        super.g = g;
        super.b = b;
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }
}
