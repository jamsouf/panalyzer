package de.jamilsoufan.panalyzer.workflow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Scan and analyze the project directory
 */
public class Analyzer {

    private final String WORKING_DIR = System.getProperty("user.dir");

    private File startHere;
    private List ignore;

    /**
     * Constructor
     */
    public Analyzer() {
        startHere = new File(".");
        ignore = new ArrayList();
        ignore.add(WORKING_DIR + "/.idea");
        ignore.add(WORKING_DIR + "/.git");
        ignore.add(WORKING_DIR + "/target");
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
                if (!ignore.contains(file.getCanonicalPath())) {
                    if (file.isDirectory()) {
                        System.out.println("directory:" + file.getCanonicalPath());
                        scanProject(file);
                    }
                    else {
                        System.out.println("     file:" + file.getCanonicalPath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
