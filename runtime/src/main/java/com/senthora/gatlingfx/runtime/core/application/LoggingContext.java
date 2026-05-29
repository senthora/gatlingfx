package com.senthora.gatlingfx.runtime.core.application;

import com.senthora.gatlingfx.runtime.core.api.RuntimeLogLevel;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.Objects;

/**
 * Temporary logging configuration context.
 * <p>
 * Applies the provided runtime log level for the
 * duration of the context and automatically restores
 * the previous logging configuration when closed.
 * <p>
 * Logging configuration is applied only to GatlingFx
 * loggers within the configured logger namespace.
 * <p>
 * <strong>Implementation Note:</strong>
 * This class is not thread-safe and should not be
 * used concurrently by multiple threads.
 * <p>
 * <strong>Usage Note:</strong>
 * Instances should always be closed to ensure the
 * previous logging configuration is restored.
 */
public final class LoggingContext implements AutoCloseable {

    private static final Logger logger = (ch.qos.logback.classic.Logger)
            LoggerFactory.getLogger("com.senthora.gatlingfx");

    private final Level previousLevel;

    private LoggingContext(Level previousLevel) {
        this.previousLevel = previousLevel;
    }

    /**
     * Applies the provided runtime log level and
     * returns a context that restores the previous
     * logging configuration when closed.
     *
     * @param logLevel runtime log level
     *
     * @return logging context
     */
    public static LoggingContext configure(RuntimeLogLevel logLevel) {
        Objects.requireNonNull(logLevel, "logLevel must not be null");

        var previousLevel = logger.getLevel();

        logger.setLevel(toLevel(logLevel));

        return new LoggingContext(previousLevel);
    }

    @Override
    public void close() {
        logger.setLevel(previousLevel);
    }

    private static Level toLevel(RuntimeLogLevel level) {
        return switch (level) {
            case TRACE -> Level.TRACE;
            case DEBUG -> Level.DEBUG;
            case INFO -> Level.INFO;
            case WARN -> Level.WARN;
            case ERROR -> Level.ERROR;
            case OFF -> Level.OFF;
        };
    }
}
