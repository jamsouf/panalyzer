package de.jamilsoufan.panalyzer.workflow;

import de.jamilsoufan.panalyzer.entities.FsObject;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Handle the workflow
 */
public class Runner {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    
    private List<FsObject> result;
    private Map<String, Object> log;

    /**
     * Execute the processes
     */
    public void runForrestRun() {
        analyzeTheProject()
                .logTheResults()
                .createTheReport();
    }

    /**
     * Start the analyzing
     *
     * @return Runner
     */
    private Runner analyzeTheProject() {
        LOGGER.info("==> Analyze folders and files...");
        
        Analyzer analyzer = new Analyzer();
        result = analyzer.perform();

        return this;
    }

    /**
     * Log the analyzing results
     *
     * @return Runner
     */
    private Runner logTheResults() {
        LOGGER.info("==> Create and log the results...");
        
        Logger logger = new Logger();
        log = logger.perform(result);

        return this;
    }

    /**
     * Use the logs and create a nice Report
     *
     * @return Runner
     */
    private Runner createTheReport() {
        LOGGER.info("==> Export the report view...");

        Reporter reporter = new Reporter();
        reporter.perform(log);

        LOGGER.info("==> View report at: {}", Reporter.DST_FOLDER);

        return this;
    }
}
