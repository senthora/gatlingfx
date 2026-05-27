package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Manages simulation log files
 * for a single runtime execution.
 */
final class SimulationLogManager {

    private final Path logDirectory;

    /**
     * Creates a new simulation log manager.
     *
     * @param logDirectoryPath path to directory where log files will be stored
     * @param runId simulation runtime execution identifier
     *
     * @throws NullPointerException if any argument is null
     * @throws SimulationRuntimeException if log directory creation failed
     */
    SimulationLogManager(Path logDirectoryPath, SimulationRunId runId) {
        Objects.requireNonNull(logDirectoryPath, "logDirectoryPath must not be null");
        Objects.requireNonNull(runId, "runId must not be null");

        var path = logDirectoryPath.resolve(runId.value());
        try {
            this.logDirectory = Files.createDirectories(path);
        }
        catch (IOException e) {
            var message = "Failed creating GatlingFx log directory (path=%s)";
            throw new SimulationRuntimeException(message.formatted(path), e);
        }
    }

    /**
     * Returns simulation log directory path.
     */
    Path logDirectory() {
        return logDirectory;
    }

    /**
     * Creates a new simulation log session.
     *
     * @param simulationClass class of simulation being executed
     *
     * @throws NullPointerException if {@code simulationClass} is null
     * @throws UncheckedIOException if log stream creation fails
     */
    SimulationLogSession createSession(Class<?> simulationClass) {
        Objects.requireNonNull(simulationClass, "simulationClass must not be null");
        var logFilePath = logDirectory.resolve(simulationClass.getSimpleName() + ".log");

        return new SimulationLogSession(logFilePath);
    }
}
