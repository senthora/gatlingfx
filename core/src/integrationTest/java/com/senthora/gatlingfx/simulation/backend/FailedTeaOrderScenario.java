package com.senthora.gatlingfx.simulation.backend;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

final class FailedTeaOrderScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Failed tea order").exec(session -> {
                    LifecycleRecorder.record(LifecycleRecorder.Event.REQUEST);
                    return session;
                })
                .exec(get("/order/tea").check(status().is(418)));
    }
}
