package com.senthora.gatlingfx.runtime.core.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SimulationLogSessionTest {

    @TempDir
    static Path tempDirectory;

    @Test
    @SuppressWarnings({"EmptyTryBlock", "DataFlowIssue"})
    @DisplayName("Should throw NullPointerException when log file path is null")
    void should_ThrowNullPointerException_when_LogFilePathIsNull() {
        var thrown = catchThrowable(() -> {
            try (var ignored = new SimulationLogSession(null)) {}
        });
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should return provided log file path when log session is created")
    void should_ReturnProvidedLogFilePath_when_LogSessionIsCreated() {
        Path logFilePath = createTempSimulationLog();

        try (var session = new SimulationLogSession(logFilePath)) {
            assertThat(session.logFilePath()).isEqualTo(logFilePath);
        }
    }

    @Test
    @DisplayName("Should write captured output to logfile when session is closed")
    void should_WriteCapturedOutputToLogFile_when_SessionIsClosed() throws IOException {
        var logFilePath = createTempSimulationLog();

        var expected = "simulation-started";
        try (var session = new SimulationLogSession(logFilePath)) {
            session.output().println(expected);
        }
        var actual = Files.readString(logFilePath);
        assertThat(actual.stripTrailing()).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("EmptyTryBlock")
    @DisplayName("Should not create log file when no output was written")
    void should_NotCreateLogFile_when_NoOutputWasWritten() {
        var logFilePath = tempDirectory
                .resolve("simulation")
                .resolve("simulation.log");

        try (var ignored = new SimulationLogSession(logFilePath)) {}

        assertThat(logFilePath).doesNotExist();
    }

    private static Path createTempSimulationLog() {
        try {
            return Files.createTempFile(
                    tempDirectory,
                    "simulation",
                    ".log"
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
