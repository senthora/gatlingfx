package com.senthora.gatlingfx.simulation.header;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class CustomTeaOrderScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Special tea order").exec(get("/order/tea")
                .check(status().is(400))
        );
    }
}
