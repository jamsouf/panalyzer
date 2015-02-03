package de.jamilsoufan.panalyzer.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * File extensions of files with mime type binary
 */
public final class BinaryExtensions {
    
    private static final List<String> LIST = new ArrayList<>();
    
    static {
        LIST.add("png");
        LIST.add("jpg");
        LIST.add("bmp");
        LIST.add("gif");
        LIST.add("tiff");
        LIST.add("svg");
        LIST.add("ai");
        LIST.add("eps");
        LIST.add("pdf");
        LIST.add("ttf");
        LIST.add("otf");
        LIST.add("eot");
        LIST.add("exe");
        LIST.add("mp3");
        LIST.add("zip");
    }

    /**
     * Utility class, no instantiation
     */
    private BinaryExtensions() {}

    /**
     * Check if the extension is a binary extension
     *
     * @param ext The extension to check
     * @return True, if the extension is a binary extension
     */
    public static Boolean inList(String ext) {
        return LIST.contains(ext == null ? null : ext.toLowerCase());
    }
}
