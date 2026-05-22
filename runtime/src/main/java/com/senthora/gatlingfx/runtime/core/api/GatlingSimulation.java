package com.senthora.gatlingfx.runtime.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a discoverable Gatling simulation.
 * <p>
 * Annotated simulations may be located automatically
 * through classpath scanning and executed by
 * GatlingFx runtime integrations.
 * <p>
 * <strong>API Note:</strong>
 * This annotation is intended for concrete simulation
 * implementations and should typically not be
 * applied to abstract base classes.
 *
 * @see SimulationScanner
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GatlingSimulation {}
