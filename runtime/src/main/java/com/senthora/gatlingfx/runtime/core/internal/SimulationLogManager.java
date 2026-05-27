package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Redirects simulation console
 * output into a dedicated log file.
 */
final class SimulationLogManager implements AutoCloseable {

    private static final String LOG_DIRECTORY_PATH = "build/gatlingfx";

    private final PrintStream output;
    private final Path logFilePath;

    /**
     * Creates a new simulation log manager.
     *
     * @param runId simulation runtime execution identifier
     * @param simulationName executed simulation name
     *
     * @throws NullPointerException if any argument is null
     * @throws UncheckedIOException if log stream creation fails
     */
    SimulationLogManager(SimulationRunId runId, String simulationName) {
        try {
            var runDirectory = createRunDirectory(runId);
            this.logFilePath = runDirectory.resolve(simulationName + ".log");

            var outputStream = Files.newOutputStream(logFilePath);
            this.output = new PrintStream(outputStream);
        }
        catch (IOException e) {
            var message = "Failed creating simulation log stream";
            throw new UncheckedIOException(message, e);
        }
    }

    @Override
    public void close() {
        output.close();
    }

    /**
     * Returns redirected simulation output stream.
     */
    PrintStream output() {
        return output;
    }

    /**
     * Returns the path to the log file.
     */
    Path logFilePath() {
        return logFilePath;
    }

    private static Path createRunDirectory(SimulationRunId runId) {
        var directory = Path.of(LOG_DIRECTORY_PATH, runId.value());
        try {
            return Files.createDirectories(directory);
        }
        catch (IOException e) {
            var message = "Failed creating GatlingFx run directory";
            throw new SimulationRuntimeException(message, e);
        }
    }
}
