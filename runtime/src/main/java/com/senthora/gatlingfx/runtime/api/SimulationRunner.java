package com.senthora.gatlingfx.runtime.api;

import com.senthora.gatlingfx.runtime.internal.DefaultSimulationRunner;

/**
 * Executes GatlingFx simulations discovered on the runtime classpath.
 * <p>
 * Implementations are responsible for locating simulation classes,
 * instantiating, and executing them through Gatling,
 * <p>
 * This abstraction is primarily intended for integration-style
 * execution inside standard JVM test tasks, allowing simulation
 * execution to participate in conventional tooling such as
 * JaCoCo coverage reporting and CI pipelines.
 */
public interface SimulationRunner {

    /**
     * Creates a new simulation runner.
     */
    static SimulationRunner create() {
        return new DefaultSimulationRunner();
    }

    /**
     * Executes simulation for a given class.
     *
     * @param simulationClass class of the simulation to run
     * @return true if the simulation executed successfully, otherwise false
     */
    boolean run(Class<?> simulationClass);

    /**
     * Execute all simulations discovered on the runtime classpath.
     *
     * @return true if all simulations executed successfully, otherwise false
     */
    boolean runAll();
}
