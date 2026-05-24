package com.senthora.gatlingfx.runtime.core.api;

/**
 * Base exception for unexpected
 * simulation runtime execution failures.
 */
public abstract class SimulationRuntimeException extends RuntimeException {

    protected SimulationRuntimeException(String message) {
        super(message);
    }

    protected SimulationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
