package com.senthora.gatlingfx.runtime.core.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

class SimulationLogSessionTest {

    @TempDir
    static Path tempDirectory;

    @Test
    @DisplayName("Should throw NullPointerException when log file path is null")
    void should_ThrowNullPointerException_when_LogFilePathIsNull() {
        assertThatThrownBy(() -> runLogSession(null, () -> {}))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw UncheckedIOException when log stream creation fails")
    void should_ThrowUncheckedIOException_when_LogStreamCreationFails() {
        assertThatThrownBy(() -> runLogSession(Path.of(""), () -> {}))
                .isInstanceOf(UncheckedIOException.class);
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
    @DisplayName("Should return redirected output stream when log session is created")
    void should_ReturnRedirectedOutputStream_when_LogSessionIsCreated() throws IOException {
        var logFilePath = createTempSimulationLog();

        var expected = "simulation-started";
        try (var session = new SimulationLogSession(logFilePath)) {
            session.output().println(expected);
        }
        var actual = Files.readString(logFilePath);
        assertThat(actual.stripTrailing()).isEqualTo(expected);
    }

    private static void runLogSession(Path logFilePath, Runnable action) {
        try (var ignored = new SimulationLogSession(logFilePath)) {
            action.run();
        }
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
