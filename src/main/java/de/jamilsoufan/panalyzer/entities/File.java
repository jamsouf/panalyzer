package de.jamilsoufan.panalyzer.entities;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Represents a File
 */
public class File extends FsObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(File.class);

    private String extension;

    /**
     * Constructor
     *  
     * @param ioObj The I/O File
     */
    public File(java.io.File ioObj) {
        super(ioObj);
        super.setType(FsType.FILE);
        setExtension(getExtension(this));
        setLinesOfCode(this);
    }

    /**
     * @param parent Parent Folder of the current File
     */
    public final void setParent(Folder parent) {
        super.setParent(parent);
        parent.addFile(this);
    }

    /**
     * @return Extension of the File
     */
    public final String getExtension() {
        return extension;
    }

    /**
     * @param extension File extension
     */
    public final void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Determine the lines of code of the file and set it
     * 
     * @param file The file to determine and set the lines of code from
     */
    public final void setLinesOfCode(File file) {
        if (isMimeTypeText(file)) {
            BigInteger loc;
            try {
                LineNumberReader lnr = new LineNumberReader(new FileReader(file.getIoObj()));
                lnr.skip(Long.MAX_VALUE);
                loc = BigInteger.valueOf(lnr.getLineNumber()).add(BigInteger.ONE);
                lnr.close();
            } catch (IOException e) {
                loc = BigInteger.ZERO;
            }
            super.setLinesOfCode(loc);
        }
    }

    /**
     * Determine the extension of the File
     * 
     * @param file The File to determine the extension from
     * @return File extension
     */
    private String getExtension(File file) {
        return FilenameUtils.getExtension(file.getFullPath()).toUpperCase();
    }

    /**
     * Check if the mime type of the file is text or binary
     *
     * @param file The file to determine if it's mime type is text or not
     * @return True, if mime type is text
     */
    private Boolean isMimeTypeText(File file) {
        if (BinaryExtensions.inList(file.getExtension().toLowerCase())) {
            return false;
        }
        
        CharsetEncoder encoder = Charset.forName("ISO-8859-1").newEncoder();
        try {
            return encoder.canEncode(FileUtils.readFileToString(file.getIoObj()));
        } catch (IOException e) {
            LOGGER.error("Unable to read file content from {}: {}", file.getIoObj().getAbsolutePath(), e.toString());
            return false;
        }
    }
}
