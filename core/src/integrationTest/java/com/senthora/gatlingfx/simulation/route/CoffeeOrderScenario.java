package com.senthora.gatlingfx.simulation.route;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class CoffeeOrderScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Order coffee").exec(get("/order/coffee")
                .check(status().is(418))
        );
    }
}
