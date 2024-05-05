package com.boomexs.shapeseditor;

import javafx.scene.control.Tab;

public class TabStructure {
    // Elf is the backend, used for storing and loading actual data
    public EntityListFile elf;
    // Tab is constructed on the fly it's the visual side of things
    public Tab tab;

    public TabStructure(EntityListFile elf, Tab tab) {
        this.elf = elf;
        this.tab = tab;
    }
}