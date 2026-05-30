package com.senthora.gatlingfx.wiremock.simulations.preparation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests preparation of a tea batch
 * using a named factory recipe.
 * <p>
 * Certain tea recipes are important enough
 * to warrant a dedicated production line.
 */
final class PrepareNamedTeaBatchScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Prepare named tea batch").exec(
                get("/recipes/imperial-earl-grey").check(status().is(200))
        );
    }
}
