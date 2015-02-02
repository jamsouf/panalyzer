package de.jamilsoufan.panalyzer.workflow;

import de.jamilsoufan.panalyzer.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Scan and analyze the project directory
 */
public class Analyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Analyzer.class);

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String WORKING_DIR = System.getProperty("user.dir") + FILE_SEPARATOR;
    public static final String START_DIR = WORKING_DIR;
    public static final String REPORT_DIR = "panalyzer-report";
    public static final String JAR_PREFIX = "jar:";
    public static java.io.File jarFile;
    public static Integer countFolders = 0;
    public static Integer countFiles = 0;

    private List<FsObject> result;
    private Folder startHere;
    private List<String> ignore;

    /**
     * Constructor
     */
    public Analyzer() {
        java.io.File start = new java.io.File(START_DIR);
        startHere = new Folder(start);
        result = new ArrayList<>();
        createIgnores();
    }

    /**
     * Detect from where the app is running
     *
     * @return Type from where the application is running
     */
    public static RunningFrom runningFrom() {
        String className = Analyzer.class.getResource("Analyzer.class").toString();
        return className.startsWith(JAR_PREFIX) ? RunningFrom.JAR : RunningFrom.FILE;
    }

    /**
     * Start the scanning
     * 
     * @return List with all scanned objects
     */
    public List<FsObject> perform() {
        scanProject(startHere);
        return result;
    }

    /**
     * Add folders / files to the ignore list
     */
    private void createIgnores() {
        ignore = new ArrayList<>();
        ignore.add(START_DIR + REPORT_DIR);
        if (RunningFrom.JAR.equals(runningFrom())) {
            try {
                jarFile = new java.io.File(Analyzer.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                ignore.add(START_DIR + jarFile.getName());
            } catch (URISyntaxException e) {
                LOGGER.error("Unable to locate JAR file: {}", e.toString());
            }
        }

        readIgnoresFromConfig();
    }

    /**
     * Read the ignores from the config file
     */
    private void readIgnoresFromConfig() {
        if (JsonConfig.isIgnoresAvailable()) {
            List<String> ignores = JsonConfig.getIgnores();
            for (String ignoreThis : ignores) {
                ignore.add(START_DIR + ignoreThis);
            }
            ignore.add(START_DIR + JsonConfig.CONFIG_FILE);
        }
    }

    /**
     * Go through the directory and subdirectories
     * 
     * @param scanningFolder The Folder to scan recursively
     */
    private void scanProject(Folder scanningFolder) {
        try {
            java.io.File[] files = scanningFolder.getIoObj().listFiles();
            if (files != null) {
                for (java.io.File ioObj : files) {
                    if (!isIgnored(ioObj.getCanonicalPath())) {
                        if (ioObj.isDirectory()) {
                            countFolders++;
                            Folder folder = new Folder(ioObj);
                            folder.setParent(scanningFolder);
                            result.add(folder);
                            scanProject(folder);
                        } else {
                            countFiles++;
                            File file = new File(ioObj);
                            file.setParent(scanningFolder);
                            result.add(file);
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error during directory scanning: {}", e.toString());
        }
    }

    /**
     * Check if the name is exact or with regex in the ignore list
     *
     * @param name The name to check
     * @return True, if the name is ignored
     */
    private Boolean isIgnored(String name) {
        if (ignore.contains(name)) {
            return true;
        }
        
        for (String regex : ignore) {
            regex = regex.replace("*", "(.*)");
            Pattern pattern = Pattern.compile(Matcher.quoteReplacement(regex));
            if (pattern.matcher(name).matches()) {
                return true;
            }
        }
        
        return false;
    }
}