package com.senthora.gatlingfx.simulation.redirect;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class RedirectedTeaOrderScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Redirected tea order").exec(get("/order/decaf-tea")
                .check(status().is(201))
        );
    }
}
