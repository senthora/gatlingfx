package com.senthora.gatlingfx.runtime.api;

import com.senthora.gatlingfx.runtime.internal.DefaultSimulationRunner;

import java.util.List;

/**
 * Executes Gatling simulations.
 * <p>
 * The runner does not automatically discover
 * Gatling simulation classes. See {@link SimulationScanner}
 * for more information on discovering simulations
 * available on the runtime classpath.
 * <p>
 * <strong>API Note:</strong>
 * This abstraction is primarily intended for executing
 * simulations in integrated JVM environments such
 * as JUnit tests, allowing execution to participate
 * in conventional tooling such as JaCoCo
 * coverage reporting and CI pipelines.
 */
public interface SimulationRunner {

    /**
     * Creates a new simulation runner.
     */
    static SimulationRunner create() {
        return new DefaultSimulationRunner();
    }

    /**
     * Executes a single Gatling simulation.
     *
     * @param simulationClass class of the simulation to run
     * @return result of simulation execution
     */
    SimulationRunResult run(Class<?> simulationClass);

    /**
     * Executes multiple Gatling simulations.
     *
     * @param simulationClasses classes of simulations to run
     * @return result of simulation executions
     */
    SimulationRunResult run(List<Class<?>> simulationClasses);
}
