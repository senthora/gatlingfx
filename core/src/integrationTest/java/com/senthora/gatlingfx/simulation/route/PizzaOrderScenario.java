package com.senthora.gatlingfx.simulation.route;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class PizzaOrderScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Order pizza").exec(get("/order/pizza")
                .check(status().is(404))
        );
    }
}
