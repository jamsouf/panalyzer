package de.jamilsoufan.panalyzer.workflow;

import java.io.File;
import java.io.IOException;

/**
 * Scan and analyze the project directory
 */
public class Analyzer {

    private File startHere;

    /**
     * Constructor
     */
    public Analyzer() {
        startHere = new File(".");
    }

    /**
     * Start the scanning
     */
    public void perform() {
        scanProject(startHere);
    }

    /**
     * Go through the directory and subdirectories
     *
     * @param dir
     */
    private void scanProject(File dir) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    scanProject(file);
                } else {
                    System.out.println("     file:" + file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
