package de.jamilsoufan.panalyzer.entities;

import de.jamilsoufan.panalyzer.workflow.Analyzer;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;

/**
 * Base type for the Folders, Files etc.
 */
public class FsObject {

    private FsType type;
    private java.io.File ioObj;
    private Folder parent;
    private String name;
    private String fullPath;
    private String relativePath;
    private String owner;
    private Long size;
    private Long lastModified;
    private BigInteger linesOfCode;

    /**
     * Constructor
     *
     * @param ioObj I/O file to put into the Object
     */
    public FsObject(File ioObj) {
        setIoObj(ioObj);
        setName(ioObj.getName());
        setFullPath(ioObj);
        setRelativePath(ioObj);
        setOwner(ioObj);
        setSize(ioObj.length());
        setLastModified(ioObj.lastModified());
        setLinesOfCode(BigInteger.ZERO);
    }

    /**
     * @return Type of the object
     */
    public FsType getType() {
        return type;
    }

    /**
     * @param type The Type to set
     */
    public void setType(FsType type) {
        this.type = type;
    }

    /**
     * @return The I/O file of the object
     */
    public File getIoObj() {
        return ioObj;
    }

    /**
     * @param ioObj The I/O file to set
     */
    public void setIoObj(File ioObj) {
        this.ioObj = ioObj;
    }

    /**
     * @return The parent Folder of the object
     */
    public Folder getParent() {
        return parent;
    }

    /**
     * @param parent Parent Folder to set
     */
    public void setParent(Folder parent) {
        this.parent = parent;
    }

    /**
     * @return The name of the object
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The full path of the object
     */
    public String getFullPath() {
        return fullPath;
    }

    /**
     * @param fullPath The full path to set
     */
    public void setFullPath(String fullPath) {
        this.fullPath = escapeWindowsName(fullPath);
    }

    /**
     * @param file The I/O file to determine and set the full path from
     */
    public void setFullPath(File file) {
        String path;
        path = file.getAbsolutePath();
        setFullPath(path);
    }

    /**
     * @return The relative path of the object
     */
    public String getRelativePath() {
        return relativePath;
    }

    /**
     * @param relativePath The relative path to set
     */
    public void setRelativePath(String relativePath) {
        this.relativePath = escapeWindowsName(relativePath);
    }

    /**
     * @param file The I/O file to determine and set the relative path from
     */
    public void setRelativePath(File file) {
        String path;
        path = file.getAbsolutePath().replace(Analyzer.START_DIR, "");
        setRelativePath(path);
    }

    /**
     * @return The owner of the object
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner The owner to set
     */
    public void setOwner(String owner) {
        this.owner = escapeWindowsName(owner);
    }

    /**
     * @param file The I/O file to determine and set the owner from
     */
    public void setOwner(File file) {
        String owner;
        try {
            owner = Files.getOwner(file.toPath()).getName();
        } catch (IOException e) {
            owner = "";
        }
        setOwner(owner);
    }

    /**
     * @return The size of the object
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size The size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return The last modified date of the object
     */
    public Long getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified The last modified date to set
     */
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return The lines of code of the object
     */
    public BigInteger getLinesOfCode() {
        return linesOfCode;
    }

    /**
     * @param linesOfCode The lines of code to set
     */
    public void setLinesOfCode(BigInteger linesOfCode) {
        this.linesOfCode = linesOfCode;
    }

    /**
     * Escape windows characters like the backslash
     *
     * @param name The name to scape
     * @return Escaped name
     */
    public static String escapeWindowsName(String name) {
        return name.replace("\\", "\\\\");
    }
}
