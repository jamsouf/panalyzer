package de.jamilsoufan.panalyzer.entities;

import de.jamilsoufan.panalyzer.workflow.Analyzer;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle the panalyzer.json config file
 */
public final class JsonConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConfig.class);
    private static final List<String> IGNORES = new ArrayList<>();
    public static final String CONFIG_FILE = "panalyzer.json";
    
    static {
        readConfig();
    }

    /**
     * Utility class, no instantiation
     */
    private JsonConfig() {}

    /**
     * Read the config file
     */
    private static void readConfig() {
        File jsonFile = new File(Analyzer.WORKING_DIR + CONFIG_FILE);
        if (jsonFile.exists() && !jsonFile.isDirectory()) {
            try {
                Object obj = JSONValue.parse(FileUtils.readFileToString(jsonFile));
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonIgnores = (JSONArray) jsonObject.get("ignores");
                for (Object ignore : jsonIgnores) {
                    IGNORES.add((String) ignore);
                }
            } catch (IOException e) {
                LOGGER.error("Error during processing of panalyzer.json: {}", e.toString());
            }
        }
    }

    /**
     * Check if ignores are in the config file
     * 
     * @return True, if ignores are available
     */
    public static Boolean isIgnoresAvailable() {
        return !IGNORES.isEmpty();
    }

    /**
     * @return Get the list of ignored folders or files
     */
    public static List<String> getIgnores() {
        return IGNORES;
    }
}
