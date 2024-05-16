package com.boomexs.shapeseditor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.io.Serializable;

/**
 * The {@code ECircle} class represents a circle entity in the shapes editor.
 * It provides methods for moving, resizing, and drawing the circle, as well as generating its graphical reference.
 */
public class ECircle extends Entity implements Serializable {
    private static final long serialVersionUID = 10L;
    public double Radius, x1, y1;

    /**
     * Constructs an {@code ECircle} with the specified color and coordinates for the center, and the radius.
     *
     * @param r      the red component of the color
     * @param g      the green component of the color
     * @param b      the blue component of the color
     * @param x      the x-coordinate of the center
     * @param y      the y-coordinate of the center
     * @param radius the radius of the circle
     */
    public ECircle(double r, double g, double b, double x, double y, double radius) {
        super.r = r;
        super.g = g;
        super.b = b;
        this.x1 = x;
        this.y1 = y;
        this.Radius = radius;
    }

    /**
     * Moves the circle by the specified x and y distances.
     *
     * @param x the distance to move along the x-axis
     * @param y the distance to move along the y-axis
     */
    @Override
    public void moveBy(double x, double y) {
        x1 += x;
        y1 += y;
        ((Circle) super.reference).setCenterX(x1);
        ((Circle) super.reference).setCenterY(y1);
    }

    /**
     * Changes the size of the circle by the specified value.
     *
     * @param x the value to change the radius by
     */
    @Override
    public void changeSizeBy(double x) {
        Radius += x;
        ((Circle) super.reference).setRadius(Radius);
    }

    /**
     * Generates the graphical reference for this circle.
     */
    @Override
    public void generateReference() {
        super.reference = new Circle(x1, y1, Radius);
        ((Shape) super.reference).setFill(Color.color(super.r, super.g, super.b));
        super.reference.setRotate(super.rotation);
    }

    /**
     * Draws the circle using two points and a rotation angle.
     * The circle is drawn with its center at {@code pointA} and its radius determined by the distance to {@code pointB}.
     *
     * @param pointA   the center point
     * @param pointB   the point on the circumference
     * @param rotation the rotation angle (not applicable for circles)
     */
    @Override
    public void drawFromPoints(Point2D pointA, Point2D pointB, double rotation) {
        x1 = pointA.getX();
        y1 = pointA.getY();
        Radius = Math.sqrt((pointA.getX() - pointB.getX()) * (pointA.getX() - pointB.getX()) + (pointA.getY() - pointB.getY()) * (pointA.getY() - pointB.getY()));

        if (super.reference == null) {
            generateReference();
        }

        ((Circle) super.reference).setCenterX(x1);
        ((Circle) super.reference).setCenterY(y1);
        ((Circle) super.reference).setRadius(Radius);
    }
}
