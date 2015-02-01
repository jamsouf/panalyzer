package de.jamilsoufan.panalyzer.workflow;

import de.jamilsoufan.panalyzer.entities.RunningFrom;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.*;
import org.slf4j.Logger;

import java.io.*;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Create the report and fill the data file
 */
public class Reporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Analyzer.class);

    private static final String REPORT_RESOURCE = "webview";
    private static final String SRC_FOLDER = Analyzer.START_DIR + "src/main/resources/" + REPORT_RESOURCE;
    public static final String DST_FOLDER = Analyzer.START_DIR + Analyzer.REPORT_DIR;
    private static final String DATA_FILE = DST_FOLDER + "/assets/js/data.js";
    private static final String BRACEL = "{";
    private static final String BRACER = "}";
    private static final String QUOTE = "'";
    private static final Integer DEFAULT_MAX = 5;
    private static final Integer FOLDERS_SIZE_MAX = 3;
    
    private Map<String, Object> log;
    private String data;

    /**
     * Execute the report actions
     * 
     * @param log Logged results
     */
    public void perform(Map<String, Object> log) {
        createReport(Analyzer.runningFrom());
        insertData(log);
    }

    /**
     * Starts the report creating
     * 
     * @param runningFrom Type from where the app is running
     */
    private void createReport(RunningFrom runningFrom) {
        if (RunningFrom.JAR.equals(runningFrom)) {
            copyReportFromJar();
        }
        else {
            copyReportFromDisk();
        }

    }

    /**
     * Copy the report resource from the disk to the report target
     */
    private void copyReportFromDisk() {
        File src = new File(SRC_FOLDER);
        File dst = new File(DST_FOLDER);
        try {
            FileUtils.copyDirectory(src, dst);
        } catch (IOException e) {
            LOGGER.error("Unable to copy report dir from disk: {}", e.toString());
        }
    }

    /**
     * Copy the report resource from the JAR to the report target
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void copyReportFromJar() {
        try {
            JarFile jar = new JarFile(Analyzer.jarFile);
            Enumeration enumEntries = jar.entries();
            while (enumEntries.hasMoreElements()) {
                JarEntry file = (JarEntry) enumEntries.nextElement();
                if (file.getName().startsWith(REPORT_RESOURCE)) {
                    File f = new File(DST_FOLDER + File.separator + file.getName().replace(REPORT_RESOURCE + "/", ""));
                    if (!file.isDirectory()) {
                        f.getParentFile().mkdirs();
                        InputStream is = jar.getInputStream(file);
                        FileOutputStream fos = new FileOutputStream(f);
                        while (is.available() > 0) {
                            fos.write(is.read());
                        }
                        fos.close();
                        is.close();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Unable to copy report dir from JAR: {}", e.toString());
        }
    }

    /**
     * Insert the data into the data file
     * 
     * @param log Logged results
     */
    private void insertData(Map<String, Object> log) {
        File dataFile = new File(DATA_FILE);
        String content;
        try {
            content = IOUtils.toString(new FileInputStream(dataFile));
            content = makeReplacements(log, content);
            IOUtils.write(content, new FileOutputStream(dataFile));
        } catch (IOException e) {
            LOGGER.error("Error during data inserting: {}", e.toString());
        }
    }

    /**
     * Replace the placeholders in the data file with values
     * 
     * @param log Logged results
     * @param data Template data from the data file
     * @return New replaced data for the data file
     */
    private String makeReplacements(Map<String, Object> log, String data) {
        this.log = log;
        this.data = data;
        
        return replaceTotal()
                .replaceTopFilesExt()
                .replaceTopFoldersFiles()
                .replaceTopFoldersSize()
                .replaceTopFoldersLoc()
                .replaceTopFilesSize()
                .replaceTopFilesLoc()
                .replaceTopExtLoc()
                .replaceTopFilesLastMod()
                .finishReplacements();
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    private Reporter replaceTotal() {
        data = data
                .replace(BRACEL + "project" + BRACER, QUOTE + log.get("project") + QUOTE)
                .replace(BRACEL + "analyzed" + BRACER, QUOTE + log.get("analyzed") + QUOTE)
                .replace(BRACEL + "totalSize" + BRACER, log.get("totalSize").toString())
                .replace(BRACEL + "totalFolders" + BRACER, log.get("totalFolders").toString())
                .replace(BRACEL + "totalFiles" + BRACER, log.get("totalFiles").toString())
                .replace(BRACEL + "totalLoc" + BRACER, log.get("totalLoc").toString());
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFilesExt() {
        Integer i = 0;
        String tplKey = "topFilesExt";
        Iterator it;
        Map.Entry pairs;
        
        Map<String, Integer> topFilesExt = (Map<String, Integer>) log.get(tplKey);
        it = topFilesExt.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFoldersFiles() {
        Integer i = 0;
        String tplKey = "topFoldersFiles";
        Iterator it;
        Map.Entry pairs;

        Map<String, Integer> topFoldersFiles = (Map<String, Integer>) log.get(tplKey);
        it = topFoldersFiles.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFoldersSize() {
        Integer i = 0;
        String tplKey = "topFoldersSize";
        Iterator it;
        Map.Entry pairs;

        Map<String, Long> topFoldersSize = (Map<String, Long>) log.get(tplKey);
        it = topFoldersSize.entrySet().iterator();
        while (it.hasNext() && i < FOLDERS_SIZE_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFoldersLoc() {
        Integer i = 0;
        String tplKey = "topFoldersLoc";
        Iterator it;
        Map.Entry pairs;

        Map<String, BigInteger> topFoldersLoc = (Map<String, BigInteger>) log.get(tplKey);
        it = topFoldersLoc.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFilesSize() {
        Integer i = 0;
        String tplKey = "topFilesSize";
        Iterator it;
        Map.Entry pairs;

        Map<String, Long> topFilesSize = (Map<String, Long>) log.get(tplKey);
        it = topFilesSize.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFilesLoc() {
        Integer i = 0;
        String tplKey = "topFilesLoc";
        Iterator it;
        Map.Entry pairs;

        Map<String, BigInteger> topFilesLoc = (Map<String, BigInteger>) log.get(tplKey);
        it = topFilesLoc.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopExtLoc() {
        Integer i = 0;
        String tplKey = "topExtLoc";
        Iterator it;
        Map.Entry pairs;

        Map<String, BigInteger> topExtLoc = (Map<String, BigInteger>) log.get(tplKey);
        it = topExtLoc.entrySet().iterator();
        while (it.hasNext() && i < DEFAULT_MAX) {
            pairs = (Map.Entry) it.next();
            data = data
                    .replace(BRACEL + tplKey + "(" + i + ")(ident)" + BRACER, QUOTE + pairs.getKey() + QUOTE)
                    .replace(BRACEL + tplKey + "(" + i + ")(value)" + BRACER, pairs.getValue().toString());
            i++;
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Replace the template data with values for this category
     * 
     * @return Reporter reference
     */
    @SuppressWarnings("unchecked")
    private Reporter replaceTopFilesLastMod() {
        Integer i = 0;
        String tplKey = "topFilesLastMod";
        Iterator it;
        Map.Entry pairs;

        Map<String, String>[] topFilesLastModArr = (Map<String, String>[]) log.get(tplKey);
        for (; i < DEFAULT_MAX; i++) {
            Map<String, String> topFilesLastMod = topFilesLastModArr[i];
            if (topFilesLastMod != null) {
                it = topFilesLastMod.entrySet().iterator();
                while (it.hasNext()) {
                    pairs = (Map.Entry) it.next();
                    data = data.replace(BRACEL + tplKey + "(" + i + ")(" + pairs.getKey() + ")" + BRACER, QUOTE + pairs.getValue() + QUOTE);
                }
            }
        }
        data = deleteEmptyPlaceholders(data, tplKey);
        return this;
    }

    /**
     * Just return the replaced data
     * 
     * @return Finished replaced data
     */
    private String finishReplacements() {
        return data;
    }

    /**
     * Replace empty placeholders (not replaced with data) with default values
     *
     * @param data Content for the data file
     * @param tplKey Placeholder key to search and replace for
     * @return With default values replaced data
     */
    private String deleteEmptyPlaceholders(String data, String tplKey) {
        return data
                .replaceAll("\\{" + tplKey + "\\([0-9]\\)\\(ident\\)\\}", "''")
                .replaceAll("\\{" + tplKey + "\\([0-9]\\)\\(value\\)\\}", "0")
                .replaceAll("\\{" + tplKey + "\\([0-9]\\)\\(owner\\)\\}", "''")
                .replaceAll("\\{" + tplKey + "\\([0-9]\\)\\(date\\)\\}", "''")
                .replaceAll("\\{" + tplKey + "\\([0-9]\\)\\(time\\)\\}", "''");
    }
}
