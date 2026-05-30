package com.senthora.gatlingfx.runtime.core.internal;

import java.io.ByteArrayOutputStream;
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
    private final ByteArrayOutputStream buffer;
    private final PrintStream output;

    /**
     * Creates a new simulation log session.
     *
     * @param logFilePath target simulation log file path
     *
     * @throws NullPointerException if {@code logFilePath} is null
     */
    SimulationLogSession(Path logFilePath) {
        Objects.requireNonNull(logFilePath, "logFilePath must not be null");

        this.logFilePath = logFilePath;
        this.buffer = new ByteArrayOutputStream();
        this.output = new PrintStream(buffer);
    }

    /**
     * Closes the session and writes any captured
     * simulation output to the log file.
     *
     * @throws UncheckedIOException if the log file cannot be written
     */
    @Override
    public void close() {
        output.close();

        if (buffer.size() == 0) {
            return;
        }
        try {
            Files.createDirectories(logFilePath.getParent());
            Files.write(logFilePath, buffer.toByteArray());
        }
        catch (IOException e) {
            var message = "Failed writing simulation log file";
            throw new UncheckedIOException(message, e);
        }
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
