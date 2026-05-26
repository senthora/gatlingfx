package com.senthora.gatlingfx.runtime.core.api;

/**
 * Exception signaling unexpected
 * simulation runtime execution failures.
 */
public class SimulationRuntimeException extends RuntimeException {

    public SimulationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
