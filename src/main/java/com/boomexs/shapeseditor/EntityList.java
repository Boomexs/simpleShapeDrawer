package com.boomexs.shapeseditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityList implements Serializable {
    private static final long serialVersionUID = 1L;
    public double width = 640, height = 480;
    private List<Entity> entities = new ArrayList<>();

    public EntityList(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void addEntity(Entity entity) {
        entity.reference = Entity.generateReference(entity);
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    // WARNING USE THIS TO ENUMERATE ONLY MODIFYING THE ARRAY DIRECTLY WILL CAUSE YOU A LOT OF PAIN
    public List<Entity> getEntities() {
        return this.entities;
    }
}
