package com.senthora.gatlingfx.runtime.engine;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;

import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassSelector;

/**
 * Internal utility that discovers
 * Gatling simulations and converts
 * them to JUnit test descriptors.
 */
final class SimulationDiscovery {

    private SimulationDiscovery() {}

    /**
     * Discovers Gatling simulations and returns
     * the root JUnit engine descriptor that contains
     * all discovered simulation descriptors.
     *
     * @param request engine discovery request
     * @param id engine unique identifier
     */
    static GatlingFxEngineDescriptor discover(
            EngineDiscoveryRequest request,
            UniqueId id
    ) {
        var engineDescriptor = new GatlingFxEngineDescriptor(id);
        var selectors = request.getSelectorsByType(ClassSelector.class);

        if (!selectors.isEmpty()) {
            for (var selector : selectors) {
                var simulationClass = selector.getJavaClass();
                if (!simulationClass.isAnnotationPresent(GatlingSimulation.class)) {
                    continue;
                }
                addDescriptor(engineDescriptor, id, simulationClass);
            }
            return engineDescriptor;
        }
        for (var simulationClass : SimulationScanner.scan()) {
            addDescriptor(engineDescriptor, id, simulationClass);
        }
        return engineDescriptor;
    }

    private static void addDescriptor(
            GatlingFxEngineDescriptor engineDescriptor,
            UniqueId id,
            Class<?> simulationClass
    ) {
        var simulationId = id.append("simulation", simulationClass.getName());

        var simulationDescriptor = new SimulationDescriptor(
                simulationId,
                simulationClass
        );
        engineDescriptor.addChild(simulationDescriptor);
    }
}
