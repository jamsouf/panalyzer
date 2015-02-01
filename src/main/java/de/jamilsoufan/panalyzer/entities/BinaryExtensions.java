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
        LIST.add("svg");
        LIST.add("ttf");
        LIST.add("otf");
        LIST.add("eot");
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
        return LIST.contains(ext);
    }
}
