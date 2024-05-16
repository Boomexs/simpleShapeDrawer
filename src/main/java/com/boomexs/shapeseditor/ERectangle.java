package com.boomexs.shapeseditor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serializable;

/**
 * The {@code ERectangle} class represents a rectangle entity in the shapes editor.
 * It provides methods for moving, resizing, and drawing the rectangle, as well as generating its graphical reference.
 */
public class ERectangle extends Entity implements Serializable {
    private static final long serialVersionUID = 30L;
    public double x1, y1, width, height;

    /**
     * Constructs an {@code ERectangle} with the specified color, position, and size.
     *
     * @param r      the red component of the color
     * @param g      the green component of the color
     * @param b      the blue component of the color
     * @param x1     the x-coordinate of the upper-left corner
     * @param y1     the y-coordinate of the upper-left corner
     * @param width  the width of the rectangle
     * @param height the height of the rectangle
     */
    public ERectangle(double r, double g, double b, double x1, double y1, double width, double height) {
        super.r = r;
        super.g = g;
        super.b = b;
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }

    /**
     * Moves the rectangle by the specified x and y distances.
     *
     * @param x the distance to move along the x-axis
     * @param y the distance to move along the y-axis
     */
    @Override
    public void moveBy(double x, double y) {

        x1 += x;
        y1 += y;
        ((Rectangle) super.reference).setX(x1);
        ((Rectangle) super.reference).setY(y1);

    }

    /**
     * Changes the size of the rectangle by the specified value.
     *
     * @param x the value to change the size by
     */
    @Override
    public void changeSizeBy(double x) {
        width += x;
        height += x;
        x1 -= x / 2;
        y1 -= x / 2;

        ((Rectangle) super.reference).setX(x1);
        ((Rectangle) super.reference).setY(y1);
        ((Rectangle) super.reference).setWidth(width);
        ((Rectangle) super.reference).setHeight(height);
    }

    /**
     * Generates the graphical reference for this rectangle.
     */
    @Override
    public void generateReference() {
        super.reference = new Rectangle(x1, y1, width, height);
        ((Shape) super.reference).setFill(Color.color(super.r, super.g, super.b));
        super.reference.setRotate(super.rotation);
    }

    /**
     * Draws the rectangle using two points and a rotation angle.
     *
     * @param pointA   the first point
     * @param pointB   the second point
     * @param rotation the rotation angle
     */
    @Override
    public void drawFromPoints(Point2D pointA, Point2D pointB, double rotation) {
        Point2D upperLeft = getUpperLeft(pointA, pointB);
        Point2D lowerRight = getLowerRight(pointA, pointB);

        x1 = upperLeft.getX();
        y1 = upperLeft.getY();
        width = lowerRight.getX() - upperLeft.getX();
        height = lowerRight.getY() - upperLeft.getY();

        if (super.reference == null) {
            generateReference();
        }

        ((Rectangle) super.reference).setX(x1);
        ((Rectangle) super.reference).setY(y1);
        ((Rectangle) super.reference).setWidth(width);
        ((Rectangle) super.reference).setHeight(height);
    }
}
