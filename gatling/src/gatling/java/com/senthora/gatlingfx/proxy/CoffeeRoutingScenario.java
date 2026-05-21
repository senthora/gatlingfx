package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class CoffeeRoutingScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Coffee routing").exec(get("/")
                .check(status().is(201))
        );
    }
}
