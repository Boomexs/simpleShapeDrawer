package com.boomexs.shapeseditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EntityList} class manages a collection of {@link Entity} objects within a defined width and height.
 * It provides methods to add and remove entities, as well as access the list of entities.
 */
public class EntityList implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Entity> entities = new ArrayList<>();
    public double width, height;

    /**
     * Constructs an {@code EntityList} with the specified width and height.
     *
     * @param width  the width of the entity list
     * @param height the height of the entity list
     */
    public EntityList(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Adds an entity to the list. The entity's graphical reference is generated before adding.
     *
     * @param entity the entity to add
     */
    public void addEntity(Entity entity) {
        entity.generateReference();
        entities.add(entity);
    }

    /**
     * Removes an entity from the list.
     *
     * @param entity the entity to remove
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Returns the list of entities. This method should be used to enumerate entities.
     * Modifying the array directly will cause issues.
     *
     * @return the list of entities
     */
    public List<Entity> getEntities() {
        return this.entities;
    }
}
