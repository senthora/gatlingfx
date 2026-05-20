package com.senthora.gatlingfx.proxy;

import io.gatling.javaapi.core.ScenarioBuilder;
import com.senthora.gatlingfx.core.SimulationScenario;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class TeaRoutingScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Tea routing").exec(get("/")
                .check(status().is(418))
        );
    }
}
