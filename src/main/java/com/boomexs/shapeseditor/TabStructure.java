package com.boomexs.shapeseditor;

import javafx.scene.control.Tab;

/**
 * The {@code TabStructure} class represents the structure of a tab in the shapes editor.
 * It includes both the backend data (`EntityListFile`) and the frontend visual representation (`Tab`).
 */
public class TabStructure {
    /**
     * The backend data for storing and loading actual data.
     */
    public EntityListFile elf;
    /**
     * The visual representation of the tab.
     */
    public Tab tab;

    private PaneEditor paneEditor;

    /**
     * Constructs a {@code TabStructure} with the specified backend data and visual tab.
     *
     * @param elf the backend data associated with the tab
     * @param tab the visual representation of the tab
     */
    public TabStructure(EntityListFile elf, Tab tab) {
        this.elf = elf;
        this.tab = tab;
    }

    /**
     * Gets the {@code PaneEditor} for this tab structure.
     *
     * @return the {@code PaneEditor} associated with this tab structure
     */
    public PaneEditor getPaneEditor() {
        return this.paneEditor;
    }

    /**
     * Sets the {@code PaneEditor} for this tab structure.
     *
     * @param paneEditor the {@code PaneEditor} to be set
     */
    public void setPaneEditor(PaneEditor paneEditor) {
        this.paneEditor = paneEditor;
    }
}