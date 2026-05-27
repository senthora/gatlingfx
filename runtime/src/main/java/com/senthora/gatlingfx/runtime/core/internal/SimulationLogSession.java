package com.senthora.gatlingfx.runtime.core.internal;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents redirected console output
 * session for a single simulation execution.
 */
final class SimulationLogSession implements AutoCloseable {

    private final Path logFilePath;
    private final PrintStream output;

    /**
     * Creates a new simulation log session.
     *
     * @param logFilePath target simulation log file path
     *
     * @throws NullPointerException if {@code logFilePath} is null
     * @throws UncheckedIOException if log stream creation fails
     */
    SimulationLogSession(Path logFilePath) {
        Objects.requireNonNull(logFilePath, "logFilePath must not be null");
        this.logFilePath = logFilePath;
        try {
            var outputStream = Files.newOutputStream(this.logFilePath);
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
     * Returns simulation log file path.
     */
    Path logFilePath() {
        return logFilePath;
    }
}
