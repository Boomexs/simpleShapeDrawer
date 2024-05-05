package com.boomexs.shapeseditor;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serializable;

public class Entity implements Serializable {
    private static final long serialVersionUID = 2L;
    public short r, g, b;
    public transient Node reference;

    public static Shape generateReference(Entity entity) {
        Shape toReturn;
        Color color = Color.rgb(entity.r, entity.g, entity.b);
        switch (entity) {
            case ECircle circle -> toReturn = new Circle(circle.x1, circle.y1, circle.Radius);
            case ERectangle rectangle ->
                    toReturn = new Rectangle(rectangle.x1, rectangle.y1, rectangle.width, rectangle.height);
            case ETriangle triangle ->
                    toReturn = new Polygon(new double[]{triangle.x1, triangle.y1, triangle.x2, triangle.y2, triangle.x3, triangle.y3});
            default -> toReturn = null;
        }
        try {
            toReturn.setFill(color);
        } catch (Exception e) {
            System.out.println("Error setting the color of an entity reference (node creation) using fall back value(black)");
            toReturn.setFill(Color.color(0, 0, 0));
        }
        return toReturn;
    }
}
