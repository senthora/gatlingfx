package com.senthora.gatlingfx.proxy.simulation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class PrepareTeaShipmentScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Tea routing").exec(get("/")
                .check(status().is(418))
        );
    }
}
