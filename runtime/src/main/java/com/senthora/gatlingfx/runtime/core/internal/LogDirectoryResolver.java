package com.senthora.gatlingfx.runtime.core.internal;

import java.nio.file.Path;

/**
 * Resolves the root directory
 * used for simulation logs.
 * <p>
 * Returns an absolute path derived from
 * system property when present. If the property
 * is not defined, the default directory is used instead.
 */
final class LogDirectoryResolver {

    static final String LOG_DIRECTORY_PROPERTY = "gatlingfx.logs.directory";
    static final String DEFAULT_LOG_DIRECTORY_PATH = "build/gatlingfx";

    private LogDirectoryResolver() {}

    /**
     * Resolves the root directory used for simulation logs.
     */
    static Path resolve() {
        var property = System.getProperty(
                LOG_DIRECTORY_PROPERTY,
                DEFAULT_LOG_DIRECTORY_PATH
        );
        return Path.of(property)
                .toAbsolutePath()
                .normalize();
    }
}
