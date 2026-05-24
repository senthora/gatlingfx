package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeException;

/**
 * Default {@link SimulationRuntimeException} implementation.
 */
final class DefaultSimulationRuntimeException extends SimulationRuntimeException {

    DefaultSimulationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
