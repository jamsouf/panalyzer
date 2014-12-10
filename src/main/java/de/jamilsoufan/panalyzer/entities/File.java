package de.jamilsoufan.panalyzer.entities;

import java.math.BigDecimal;

/**
 * Entity file
 */
public class File extends FsObject {

    private String name;
    private String extension;
    private BigDecimal size;
    private Folder folder;
    private Integer lines;

    public File() {
        setType(FsType.FILE);
    }
}
