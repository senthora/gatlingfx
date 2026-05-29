package com.senthora.gatlingfx.runtime.core.api;

/**
 * Runtime logging levels supported
 * by GatlingFx runtime execution.
 * <p>
 * Levels follow the standard SLF4J
 * logging hierarchy from TRACE to OFF.
 */
public enum RuntimeLogLevel {

    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    OFF
}
