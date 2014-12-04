package de.jamilsoufan.panalyzer.app;

import de.jamilsoufan.panalyzer.workflow.Runner;

/**
 * Project analyzer
 * A tool to go through your project or
 * anything else and analyze it's content.
 * Nice web report will be created.
 *
 * @author Jamil Soufan
 * @url https://github.com/jamsouf/panalyzer
 * @license MIT
 */
public class Panalyzer {

    /**
     * Entry point of the application
     *
     * @param args
     */
    public static void main(String[] args) {
        Runner runnner = new Runner();
        runnner.runForrestRun();
    }
}