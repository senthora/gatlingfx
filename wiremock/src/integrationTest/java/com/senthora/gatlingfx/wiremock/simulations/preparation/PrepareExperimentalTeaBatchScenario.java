package com.senthora.gatlingfx.wiremock.simulations.preparation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests preparation of an experimental tea batch.
 * <p>
 * Some recipes are too unconventional
 * for any established production line.
 */
final class PrepareExperimentalTeaBatchScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Prepare experimental tea batch").exec(
                get("/recipes/experimental").check(status().is(418))
        );
    }
}
