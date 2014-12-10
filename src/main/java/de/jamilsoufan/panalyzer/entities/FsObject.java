package de.jamilsoufan.panalyzer.entities;

/**
 * Base type for the folders, files etc.
 */
public abstract class FsObject {

    private FsType type;

    public FsType getType() {
        return type;
    }

    public void setType(FsType type) {
        this.type = type;
    }
}
