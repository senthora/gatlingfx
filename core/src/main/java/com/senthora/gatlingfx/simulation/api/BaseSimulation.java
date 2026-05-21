package com.senthora.gatlingfx.simulation.api;

import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.Simulation;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.global;

/**
 * Base class for simulations that need more structure
 * than a single self-contained Gatling scenario.
 * <p>
 * This abstraction treats backend preparation, simulation execution,
 * and post-run verification as parts of the same simulation flow,
 * making it easier to build reusable infrastructure-oriented tests.
 */
public abstract class BaseSimulation extends Simulation {

    /**
     * Creates and configures the simulation.
     * <p>
     * Subclasses are expected to provide protocol configuration,
     * simulation scenarios, and optional custom assertions.
     */
    protected BaseSimulation() {
        setUp(setup()).protocols(protocol().build()).assertions(assertions());
    }

    @Override
    public final void before() {
        backendClient().setup();
    }

    @Override
    public final void after() {
        try {
            verify();
        }
        catch (Throwable t) {
            throw new RuntimeException("Failed verifying simulation", t);
        }
        finally {
            backendClient().teardown();
        }
    }

    /**
     * Performs additional verification after the simulation completes.
     * <p>
     * Intended for checking backend requests, response behavior,
     * or other results produced during simulation execution.
     */
    @SuppressWarnings("EmptyMethod")
    protected void verify() {}

    /**
     * Returns the backend client used by the simulation.
     * <p>
     * By default, returns a no-op client implementation.
     */
    protected BackendClient backendClient() {
        return BackendClient.stub();
    }

    /**
     * Creates the population setup for all configured simulation scenarios.
     * <p>
     * By default, each scenario is executed
     * with a single immediately injected user.
     */
    protected PopulationBuilder[] setup() {
        return scenarios().stream()
                .map(c -> c.build().injectOpen(atOnceUsers(1)))
                .toArray(PopulationBuilder[]::new);
    }

    /**
     * Returns the HTTP protocol configuration used by the simulation.
     * <p>
     * Implementations are expected to configure the
     * protocol definition, while protocol construction
     * is handled internally by the framework.
     */
    protected abstract SimulationProtocol protocol();

    /**
     * Returns the simulation scenarios executed as part of the simulation.
     * <p>
     * Scenarios are intended to represent reusable
     * request flows or other test interaction sequences.
     */
    protected List<SimulationScenario> scenarios() {
        return List.of();
    }

    /**
     * Returns the assertions applied after simulation execution.
     * <p>
     * By default, the simulation asserts that no requests failed.
     */
    protected Assertion[] assertions() {
        return new Assertion[]{
                global().failedRequests().count().is(0L)
        };
    }
}
