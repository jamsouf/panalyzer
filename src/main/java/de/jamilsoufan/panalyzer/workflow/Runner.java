package de.jamilsoufan.panalyzer.workflow;

/**
 * Handle the workflow
 */
public class Runner {

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
     * @return
     */
    private Runner analyzeTheProject() {
        Analyzer analyzer = new Analyzer();
        analyzer.perform();
        logTheResults();

        return this;
    }

    /**
     * Log the analyzing results
     *
     * @return
     */
    private Runner logTheResults() {
        Logger logger = new Logger();
        logger.perform();
        createTheReport();

        return this;
    }

    /**
     * Use the logs and create a nice Report
     *
     * @return
     */
    private Runner createTheReport() {
        Reporter reporter = new Reporter();
        reporter.perform();

        return this;
    }
}
