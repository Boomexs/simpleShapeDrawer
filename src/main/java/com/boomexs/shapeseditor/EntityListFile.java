package com.boomexs.shapeseditor;

import java.io.*;

/**
 * The {@code EntityListFile} class handles the loading and saving of {@code EntityList} objects
 * from and to files. It provides methods for serializing and deserializing {@code EntityList}
 * instances.
 */
public class EntityListFile {
    public File file;
    public EntityList entityList;

    /**
     * Constructs a new {@code EntityListFile} with a default {@code EntityList} of size 1280x720.
     */
    public EntityListFile() {
        entityList = new EntityList(1280, 720);
    }

    /**
     * Constructs a new {@code EntityListFile} by loading an {@code EntityList} from the specified file.
     *
     * @param file the file from which to load the {@code EntityList}
     * @throws IOException if an I/O error occurs during loading
     */
    public EntityListFile(File file) throws IOException {
        this.file = file;
        Load(file);
    }

    /**
     * Constructs a new {@code EntityListFile} with the specified {@code EntityList}.
     *
     * @param entityList the {@code EntityList} to be managed by this file
     */
    public EntityListFile(EntityList entityList) {
        this.entityList = entityList;
    }

    /**
     * Loads an {@code EntityList} from the specified file.
     *
     * @param file the file from which to load the {@code EntityList}
     * @throws IOException if an I/O error occurs during loading
     */
    public void Load(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            this.entityList = (EntityList) objectInputStream.readObject();
            this.entityList.getEntities().forEach(Entity::generateReference);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.entityList = null;
        }

    }

    /**
     * Saves the specified {@code EntityList} to the specified file.
     *
     * @param file       the file to which the {@code EntityList} should be saved
     * @param entityList the {@code EntityList} to be saved
     * @throws IOException if an I/O error occurs during saving
     */
    public void Save(File file, EntityList entityList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(entityList);

    }
}
