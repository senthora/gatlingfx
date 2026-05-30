package com.senthora.gatlingfx.wiremock.simulations.preparation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests preparation of seasonal tea batches.
 * <p>
 * Seasonal recipes come and go, making a
 * dedicated production line impractical.
 */
final class PrepareSeasonalTeaBatchScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Prepare seasonal tea batches")
                .exec(get("/recipes/spring-jasmine").check(status().is(200)))
                .exec(get("/recipes/summer-oolong").check(status().is(200)));
    }
}
