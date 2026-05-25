package com.senthora.gatlingfx.runtime.core.api;

import com.senthora.gatlingfx.runtime.core.internal.DefaultGatlingRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationRuntime;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import java.util.List;

/**
 * Executes GatlingFx simulations.
 * <p>
 * The runner does not automatically discover
 * GatlingFx simulation classes. See {@link SimulationScanner}
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
        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner);

        return new DefaultSimulationRunner(runtime);
    }

    /**
     * Executes a single GatlingFx simulation.
     *
     * @param simulationClass class of the simulation to run
     * @return result of executing the provided simulation
     *
     * @throws NullPointerException if {@code simulationClass} is null
     */
    SimulationRunResult run(Class<? extends BaseSimulation> simulationClass);

    /**
     * Executes multiple GatlingFx simulations.
     * <p>
     * <strong>API Note:</strong>
     * Simulation execution order is delegated
     * to Gatling and cannot be guaranteed.
     *
     * @param simulationClasses classes of simulations to run
     * @return result of executing the provided simulations,
     * or an empty successful result if no simulations are provided
     *
     * @throws NullPointerException if {@code simulationClasses}
     * is null or contains null elements
     */
    SimulationRunResult run(List<Class<? extends BaseSimulation>> simulationClasses);
}
