package com.senthora.gatlingfx.runtime.core.internal;

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
     * @throws IllegalArgumentException if runId is blank
     */
    SimulationLogManager(Path logDirectoryPath, String runId) {
        Objects.requireNonNull(logDirectoryPath, "logDirectoryPath must not be null");
        Objects.requireNonNull(runId, "runId must not be null");
        if (runId.isBlank()) {
            throw new IllegalArgumentException("runId must not be blank");
        }
        this.logDirectory = logDirectoryPath.resolve(runId);
    }

    /**
     * Returns the directory where log files
     * for simulation runtime execution are stored.
     */
    Path logDirectory() {
        return logDirectory;
    }

    /**
     * Creates and returns a new simulation
     * log session for the specified simulation class.
     *
     * @param simulationClass class of simulation being executed
     *
     * @throws NullPointerException if {@code simulationClass} is null
     */
    SimulationLogSession createSession(Class<?> simulationClass) {
        Objects.requireNonNull(simulationClass, "simulationClass must not be null");
        var logFilePath = logDirectory.resolve(simulationClass.getSimpleName() + ".log");

        return new SimulationLogSession(logFilePath);
    }
}
