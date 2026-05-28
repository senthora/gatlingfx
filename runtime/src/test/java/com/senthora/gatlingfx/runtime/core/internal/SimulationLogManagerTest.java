package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;
import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SimulationLogManagerTest {

    @TempDir
    static Path tempDirectory;

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when log directory path is null")
        void should_ThrowNullPointerException_when_LogDirectoryPathIsNull() {
            var thrown = catchThrowable(() -> new SimulationLogManager(
                    null,
                    "run-123"
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when run id is null")
        void should_ThrowNullPointerException_when_RunIdIsNull() {
            var thrown = catchThrowable(() -> new SimulationLogManager(
                    tempDirectory,
                    null
            ));
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when run id is blank")
        void should_ThrowIllegalArgumentException_when_RunIdIsBlank() {
            var thrown = catchThrowable(() -> new SimulationLogManager(
                    tempDirectory,
                    " "
            ));
            assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw SimulationRuntimeException when log directory creation fails")
        void should_ThrowSimulationRuntimeException_when_LogDirectoryCreationFails() throws IOException {
            var logDirectoryPath = Files.createTempFile(
                    tempDirectory,
                    "gatlingfx",
                    ".log"
            );
            var thrown = catchThrowable(() -> new SimulationLogManager(
                    logDirectoryPath,
                    "run-123"
            ));
            assertThat(thrown).isInstanceOf(SimulationRuntimeException.class);
        }

        @Test
        @DisplayName("Should return created log directory when log manager is created")
        void should_ReturnCreatedLogDirectory_when_LogManagerIsCreated() {
            var logDirectoryPath = tempDirectory.resolve("logs");
            var runId = "run-123";

            var logManager = new SimulationLogManager(logDirectoryPath, runId);

            assertThat(logManager.logDirectory())
                    .exists()
                    .isDirectory()
                    .isEqualTo(logDirectoryPath.resolve(runId));
        }
    }

    @Nested
    @DisplayName("createSession")
    class CreateSessionMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when simulation class is null")
        void should_ThrowNullPointerException_when_SimulationClassIsNull() {
            var logManager = new SimulationLogManager(
                    tempDirectory,
                    "run-123"
            );
            var thrown = catchThrowable(() -> {
                //noinspection EmptyTryBlock
                try (var ignored = logManager.createSession(null)) {}
            });
            assertThat(thrown).isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return simulation log session when simulation class is valid")
        void should_ReturnSimulationLogSession_when_SimulationClassIsValid() {
            var logManager = new SimulationLogManager(
                    tempDirectory,
                    "run-123"
            );
            try (var session = logManager.createSession(TestSimulation.class)) {
                assertThat(session).isNotNull();
            }
        }

        @Test
        @DisplayName("Should create log file using simulation class name when session is created")
        void should_CreateLogFileUsingSimulationClassName_when_SessionIsCreated() {
            var logManager = new SimulationLogManager(
                    tempDirectory,
                    "run-123"
            );
            try (var ignored = logManager.createSession(TestSimulation.class)) {
                var expected = logManager.logDirectory().resolve("TestSimulation.log");
                assertThat(expected).exists().isRegularFile();
            }
        }
    }
}
