/**
 * The MIT License (MIT)
 *
 * Copyright (c) Jamil Soufan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * Project analyzer
 * A tool to go through your project or
 * anything else and analyze it's content.
 * Nice web report will be created.
 *
 * @author Jamil Soufan
 * @version 1.0.0
 * @see <a href="https://github.com/jamsouf/panalyzer">github.com/jamsouf/panalyzer</a>
 */

package de.jamilsoufan.panalyzer.app;

import com.google.common.base.Stopwatch;
import de.jamilsoufan.panalyzer.workflow.Analyzer;
import de.jamilsoufan.panalyzer.workflow.Runner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Application entry point
 */
public final class Panalyzer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Panalyzer.class);

    /**
     * Utility class, no instantiation
     */
    private Panalyzer() {}

    /**
     * Entry point of the application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Starting panalyzer for: {}", Analyzer.START_DIR);
        Stopwatch stopwatch = Stopwatch.createStarted();
        
        Runner runnner = new Runner();
        runnner.runForrestRun();
        
        stopwatch.stop();
        LOGGER.info("Finish. Analyzed folders: {} / files: {}. Duration: {}", Analyzer.countFolders, Analyzer.countFiles, stopwatch);
    }
}