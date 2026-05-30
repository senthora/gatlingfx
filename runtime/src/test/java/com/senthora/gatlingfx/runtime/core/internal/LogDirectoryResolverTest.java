package com.senthora.gatlingfx.runtime.core.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class LogDirectoryResolverTest {

    @TempDir
    Path tempDirectory;

    @AfterEach
    void teardownResolveMethodTest() {
        System.clearProperty(LogDirectoryResolver.LOG_DIRECTORY_PROPERTY);
    }

    @Test
    @DisplayName("Should return path derived from system property when property is defined")
    void should_ReturnPathDerivedFromSystemProperty_when_PropertyIsDefined() {
        var logDirectory = tempDirectory.resolve("logs");
        System.setProperty(
                LogDirectoryResolver.LOG_DIRECTORY_PROPERTY,
                logDirectory.toString()
        );
        assertThat(LogDirectoryResolver.resolve())
                .isEqualTo(logDirectory.toAbsolutePath().normalize());
    }

    @Test
    @DisplayName("Should return path derived from default directory when property is not defined")
    void should_ReturnPathDerivedFromDefaultDirectory_when_PropertyIsNotDefined() {
        var expected = Path.of(LogDirectoryResolver.DEFAULT_LOG_DIRECTORY_PATH)
                .toAbsolutePath()
                .normalize();

        assertThat(LogDirectoryResolver.resolve()).isEqualTo(expected);
    }
}
