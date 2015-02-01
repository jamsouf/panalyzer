package de.jamilsoufan.panalyzer.workflow;

import com.google.common.collect.Iterables;
import de.jamilsoufan.panalyzer.entities.File;
import de.jamilsoufan.panalyzer.entities.Folder;
import de.jamilsoufan.panalyzer.entities.FsObject;
import de.jamilsoufan.panalyzer.entities.FsType;
import de.jamilsoufan.panalyzer.comparator.BigIntegerComparator;
import de.jamilsoufan.panalyzer.comparator.IntegerComparator;
import de.jamilsoufan.panalyzer.comparator.LongComparator;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Process the analyzed results and put it into a log object
 */
public class Logger {

    private static final String PROP_SIZE = "size";
    private static final String PROP_FOLDERS = "folders";
    private static final String PROP_FILES = "files";
    private static final String PROP_LOC = "loc";
    private Map<String, Object> log;

    /**
     * Constructor
     */
    public Logger() {
        log = new HashMap<>();
    }

    /**
     * Fill the log object with the analyzed data
     * 
     * @param result Result list from the analyzing
     * @return Log list
     */
    public Map<String, Object> perform(List<FsObject> result) {
        fillLog(result);
        return log;
    }

    /**
     * Fill the log object with the data
     * 
     * @param result Result list from the analyzing
     */
    public void fillLog(List<FsObject> result) {
        Map<String, BigInteger> totalValues = getTotalValues(result);

        log.put("project", Analyzer.START_DIR);
        log.put("analyzed", new SimpleDateFormat("EEE, dd MMM yyyy HH:mm").format(new Date()));
        log.put("totalSize", totalValues.get(PROP_SIZE));
        log.put("totalFolders", totalValues.get(PROP_FOLDERS));
        log.put("totalFiles", totalValues.get(PROP_FILES));
        log.put("totalLoc", totalValues.get(PROP_LOC));
        log.put("topFilesExt", getTopFilesExt(result));
        log.put("topFoldersFiles", getTopFoldersFiles(result));
        log.put("topFoldersSize", getTopFoldersSize(result));
        log.put("topFoldersLoc", getTopFoldersLoc(result));
        log.put("topFilesSize", getTopFilesSize(result));
        log.put("topFilesLoc", getTopFilesLoc(result));
        log.put("topExtLoc", getTopExtLoc(result));
        log.put("topFilesLastMod", getTopFilesLastMod(result));
    }

    /**
     * Calculate the total values
     * 
     * @param result Result list from the analyzing
     * @return Total values
     */
    public Map<String, BigInteger> getTotalValues(List<FsObject> result) {
        Map<String, BigInteger> totalValues = new HashMap<>();
        totalValues.put(PROP_SIZE, BigInteger.ZERO);
        totalValues.put(PROP_FOLDERS, BigInteger.ZERO);
        totalValues.put(PROP_FILES, BigInteger.ZERO);
        totalValues.put(PROP_LOC, BigInteger.ZERO);

        for (FsObject fsObject : result) {
            if (FsType.FOLDER.equals(fsObject.getType())) {
                totalValues.put(PROP_FOLDERS, totalValues.get(PROP_FOLDERS).add(BigInteger.ONE));
            }
            else if(FsType.FILE.equals(fsObject.getType())) {
                totalValues.put(PROP_SIZE, totalValues.get(PROP_SIZE).add(BigInteger.valueOf(fsObject.getSize())));
                totalValues.put(PROP_FILES, totalValues.get(PROP_FILES).add(BigInteger.ONE));
                totalValues.put(PROP_LOC, totalValues.get(PROP_LOC).add(fsObject.getLinesOfCode()));
            }
        }

        return totalValues;
    }

    /**
     * Calculate the top files by extension
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, Integer> getTopFilesExt(List<FsObject> result) {
        String extension;
        Map<String, Integer> map = new HashMap<>();
        IntegerComparator vc = new IntegerComparator(map);
        Map<String, Integer> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                extension = ((File) fsObject).getExtension();
                map.put(extension, 1 + (map.get(extension) == null ? Integer.valueOf(0) : map.get(extension)));
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top folders by files
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, Integer> getTopFoldersFiles(List<FsObject> result) {
        String path;
        Map<String, Integer> map = new HashMap<>();
        IntegerComparator vc = new IntegerComparator(map);
        Map<String, Integer> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FOLDER.equals(fsObject.getType())) {
                Folder folder = (Folder) fsObject;
                path = folder.getRelativePath();
                map.put(path, folder.getFiles().size());
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top folders by size
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, Long> getTopFoldersSize(List<FsObject> result) {
        String path;
        Map<String, Long> map = new HashMap<>();
        LongComparator vc = new LongComparator(map);
        Map<String, Long> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FOLDER.equals(fsObject.getType())) {
                Folder folder = (Folder) fsObject;
                path = folder.getRelativePath();
                map.put(path, folder.getSize());
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top folders by lines of code
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, BigInteger> getTopFoldersLoc(List<FsObject> result) {
        String path;
        Map<String, BigInteger> map = new HashMap<>();
        BigIntegerComparator vc = new BigIntegerComparator(map);
        Map<String, BigInteger> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FOLDER.equals(fsObject.getType())) {
                Folder folder = (Folder) fsObject;
                path = folder.getRelativePath();
                map.put(path, folder.getLinesOfCode());
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top files by size
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, Long> getTopFilesSize(List<FsObject> result) {
        String fileName;
        Map<String, Long> map = new HashMap<>();
        LongComparator vc = new LongComparator(map);
        Map<String, Long> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                File file = (File) fsObject;
                fileName = file.getRelativePath();
                map.put(fileName, file.getSize());
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top files by lines of code
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, BigInteger> getTopFilesLoc(List<FsObject> result) {
        String fileName;
        Map<String, BigInteger> map = new HashMap<>();
        BigIntegerComparator vc = new BigIntegerComparator(map);
        Map<String, BigInteger> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                File file = (File) fsObject;
                fileName = file.getRelativePath();
                map.put(fileName, file.getLinesOfCode());
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top extensions by lines of code
     * 
     * @param result Result list from the analyzing
     * @return Top values
     */
    public Map<String, BigInteger> getTopExtLoc(List<FsObject> result) {
        String extension;
        Map<String, BigInteger> map = new HashMap<>();
        BigIntegerComparator vc = new BigIntegerComparator(map);
        Map<String, BigInteger> sortedMap = new TreeMap<>(vc);

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                File file = (File) fsObject;
                extension = file.getExtension();
                map.put(extension, file.getLinesOfCode().add(map.get(extension) == null ? BigInteger.ZERO : map.get(extension)));
            }
        }

        sortedMap.putAll(map);

        return sortedMap;
    }

    /**
     * Calculate the top files by last modified date
     *
     * @param result Result list from the analyzing
     * @return Top values
     */
    @SuppressWarnings("unchecked")
    public Map<String, String>[] getTopFilesLastMod(List<FsObject> result) {
        String fileName;
        final Integer max = 5;
        Integer pos;
        Map<String, Long> map = new HashMap<>();
        LongComparator vc = new LongComparator(map);
        Map<String, Long> sortedMap = new TreeMap<>(vc);
        Map<String, String>[] resultMap = new HashMap[max];

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                File file = (File) fsObject;
                fileName = file.getRelativePath();
                map.put(fileName, file.getLastModified());
            }
        }

        sortedMap.putAll(map);

        for (FsObject fsObject : result) {
            if (FsType.FILE.equals(fsObject.getType())) {
                pos = null;
                File file = (File) fsObject;
                for (int i=0; i<max && i<sortedMap.size(); i++) {
                    if (file.getRelativePath().equals(Iterables.get(sortedMap.entrySet(), i).getKey())) {
                        pos = i;
                    }
                }
                if (pos != null) {
                    Date date = new Date(file.getLastModified());
                    Map<String, String> itemMap = new HashMap<>();
                    itemMap.put("ident", file.getRelativePath());
                    itemMap.put("owner", file.getOwner());
                    itemMap.put("date", new SimpleDateFormat("yyyy/MM/dd").format(date));
                    itemMap.put("time", new SimpleDateFormat("HH:mm").format(date));
                    resultMap[pos] = itemMap;
                }
            }
        }

        return resultMap;
    }
}