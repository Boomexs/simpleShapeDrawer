package com.boomexs.shapeseditor;

import java.io.*;

// OFTEN ABBREVIATED AS ELF
public class EntityListFile {
    public File file;
    public EntityList entityList;

    public EntityListFile() {
        entityList = new EntityList(640, 480);
    }

    ;

    public EntityListFile(File file) throws IOException {
        this.file = file;
        Load(file);
    }

    public EntityListFile(EntityList entityList) {
        this.entityList = entityList;
    }

    public void Load(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            this.entityList = (EntityList) objectInputStream.readObject();
            this.entityList.getEntities().forEach(entity -> {
                entity.reference = Entity.generateReference(entity);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.entityList = null;
        }

    }

    public void Save(File file, EntityList entityList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(entityList);

    }
}
