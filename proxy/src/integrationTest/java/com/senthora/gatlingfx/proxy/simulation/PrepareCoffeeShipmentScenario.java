package com.senthora.gatlingfx.proxy.simulation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Dispatches a coffee shipment to the warehouse.
 * <p>
 * Coffee shipments should arrive
 * at the designated coffee warehouse.
 */
final class PrepareCoffeeShipmentScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Dispatch coffee shipment").exec(get("/")
                .check(status().is(201))
        );
    }
}
