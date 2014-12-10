package de.jamilsoufan.panalyzer.entities;

import java.util.List;

/**
 * Entity folder
 */
public class Folder extends FsObject {

    private String name;
    private String path;
    private Folder folder;
    private List<File> files;

    public Folder() {
        setType(FsType.FOLDER);
    }
}
