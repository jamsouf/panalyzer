package de.jamilsoufan.panalyzer.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a folder
 */
public class Folder extends FsObject {

    private List<File> files;

    /**
     * Constructor
     * 
     * @param ioObj The I/O File
     */
    public Folder(java.io.File ioObj) {
        super(ioObj);
        super.setType(FsType.FOLDER);
        files = new ArrayList<>();
    }

    /**
     * @return List of Files in the Folder
     */
    public final List<File> getFiles() {
        return files;
    }

    /**
     * @param files List of Files to set
     */
    public final void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * Add a File to the Folders Filelist.
     * Increments the size and lines of codes of the Folder
     *
     * @param file The File to add
     */
    public final void addFile(File file) {
        files.add(file);
        addSize(file.getSize());
        addLinesOfCode(file.getLinesOfCode());
    }

    /**
     * @param loc Number of lines of code to add
     */
    public final void addLinesOfCode(BigInteger loc) {
        super.setLinesOfCode(getLinesOfCode().add(loc));
    }

    /**
     * @param size Size to add
     */
    public final void addSize(Long size) {
        super.setSize(getSize() + size);
    }
}
