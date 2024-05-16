package com.boomexs.shapeseditor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.io.Serializable;

/**
 * The {@code Entity} class represents a graphical entity in the shapes editor.
 * It provides common properties and methods for all entities, such as color, rotation,
 * and position. This class is abstract and must be subclassed to create specific types of entities.
 */
public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 2L;
    public double r;
    public double g;
    public double b;
    public double rotation = 0.0;
    public transient Node reference;

    /**
     * Returns the upper left point given two points to make rectangle.
     *
     * @param pointA the first point of a rectangle
     * @param pointB the second point of a rectangle
     * @return the upper left point of a rectangle
     */
    static public Point2D getUpperLeft(Point2D pointA, Point2D pointB) {
        return new Point2D(Double.min(pointA.getX(), pointB.getX()), Double.min(pointA.getY(), pointB.getY()));
    }

    /**
     * Returns the lower right point given two points to make rectangle.
     *
     * @param pointA the first point of a rectangle
     * @param pointB the second point of a rectangle
     * @return the lower right point of a rectangle
     */
    static public Point2D getLowerRight(Point2D pointA, Point2D pointB) {
        return new Point2D(Double.max(pointA.getX(), pointB.getX()), Double.max(pointA.getY(), pointB.getY()));
    }

    /**
     * Generates the graphical reference for this entity. This method must be implemented by subclasses.
     */
    abstract public void generateReference();

    /**
     * Draws the entity using two points and a rotation angle. This method must be implemented by subclasses.
     *
     * @param pointA   the first point
     * @param pointB   the second point
     * @param rotation the rotation angle
     */
    abstract public void drawFromPoints(Point2D pointA, Point2D pointB, double rotation);

    /**
     * Moves the entity by the specified x and y distances. This method must be implemented by subclasses.
     *
     * @param x the distance to move along the x-axis
     * @param y the distance to move along the y-axis
     */
    abstract public void moveBy(double x, double y);

    /**
     * Changes the size of the entity by the specified x value. This method must be implemented by subclasses.
     *
     * @param x the value to change the size by
     */
    abstract public void changeSizeBy(double x);

    /**
     * Sets the color of this entity.
     *
     * @param color the new color
     */
    public void setColor(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();

        if (reference instanceof Shape) {
            ((Shape) reference).setFill(color);
        }
    }

    /**
     * Sets the rotation of this entity.
     *
     * @param rotation the new rotation angle
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
        if (reference instanceof Shape) {
            reference.setRotate(rotation);
        }
    }
}
