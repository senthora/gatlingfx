package com.senthora.gatlingfx.runtime.engine;

import org.junit.platform.engine.*;

/**
 * JUnit Platform engine implementation
 * for executing Gatling simulations.
 * <p>
 * Discovered simulations are exposed as native
 * JUnit test executions, enabling integration
 * with Gradle test tasks, IDE test explorers,
 * and standard JVM testing infrastructure.
 */
public final class GatlingFxTestEngine implements TestEngine {

    private static final String ENGINE_ID = "gatlingfx";

    @Override
    public String getId() {
        return ENGINE_ID;
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest request, UniqueId id) {
        return SimulationDiscovery.discover(request, id);
    }

    @Override
    public void execute(ExecutionRequest request) {
        SimulationExecutor.execute(request);
    }
}
