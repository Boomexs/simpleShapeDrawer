package com.boomexs.shapeseditor;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.Serializable;

/**
 * The {@code ETriangle} class represents a triangle entity in the shapes editor.
 * It provides methods for moving, resizing, and drawing the triangle, as well as generating its graphical reference.
 */
public class ETriangle extends Entity implements Serializable {
    private static final long serialVersionUID = 20L;
    public double x1, y1, x2, y2, x3, y3;

    /**
     * Constructs an {@code ETriangle} with the specified color and coordinates for the three vertices.
     *
     * @param r  the red component of the color
     * @param g  the green component of the color
     * @param b  the blue component of the color
     * @param x1 the x-coordinate of the first vertex
     * @param y1 the y-coordinate of the first vertex
     * @param x2 the x-coordinate of the second vertex
     * @param y2 the y-coordinate of the second vertex
     * @param x3 the x-coordinate of the third vertex
     * @param y3 the y-coordinate of the third vertex
     */
    public ETriangle(double r, double g, double b, double x1, double y1, double x2, double y2, double x3, double y3) {
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

    /**
     * Moves the triangle by the specified x and y distances.
     *
     * @param x the distance to move along the x-axis
     * @param y the distance to move along the y-axis
     */
    @Override
    public void moveBy(double x, double y) {

        x1 += x;
        y1 += y;
        x2 += x;
        y2 += y;
        x3 += x;
        y3 += y;

        setTrianglePoints();

    }

    /**
     * Changes the size of the triangle by the specified value.
     *
     * @param x the value to change the size by
     */
    @Override
    public void changeSizeBy(double x) {
        x1 -= x / 2;
        y1 += x / 2;
        //x2 should remain unchanged
        y2 -= x / 2;
        x3 += x / 2;
        y3 += x / 2;

        setTrianglePoints();
    }

    /**
     * Updates the points of the triangle in the graphical representation.
     */
    private void setTrianglePoints() {
        try {
            ((Polygon) super.reference).getPoints().set(0, x1);
            ((Polygon) super.reference).getPoints().set(1, y1);
            ((Polygon) super.reference).getPoints().set(2, x2);
            ((Polygon) super.reference).getPoints().set(3, y2);
            ((Polygon) super.reference).getPoints().set(4, x3);
            ((Polygon) super.reference).getPoints().set(5, y3);
        } catch (NullPointerException e) {
            System.out.println("updatePosition triangle done goofed");
        }
    }

    /**
     * Generates the graphical reference for this triangle.
     */
    @Override
    public void generateReference() {
        super.reference = new Polygon(x1, y1, x2, y2, x3, y3);
        ((Shape) super.reference).setFill(Color.color(super.r, super.g, super.b));
        super.reference.setRotate(super.rotation);
    }

    /**
     * Draws the triangle using two points and a rotation angle.
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
        y1 = lowerRight.getY();
        x2 = upperLeft.getX() + ((lowerRight.getX() - upperLeft.getX()) / 2);
        y2 = upperLeft.getY();
        x3 = lowerRight.getX();
        y3 = lowerRight.getY();

        if (super.reference == null) {
            generateReference();
        }
        setTrianglePoints();
    }
}
