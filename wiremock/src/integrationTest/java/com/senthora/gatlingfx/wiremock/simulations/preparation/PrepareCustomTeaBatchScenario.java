package com.senthora.gatlingfx.wiremock.simulations.preparation;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests preparation of a custom tea batch.
 * <p>
 * The factory accepts special instructions,
 * regardless of how questionable they may be.
 */
final class PrepareCustomTeaBatchScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Prepare custom tea batch").exec(get("/recipes/custom-blend")
                .header("X-Customer-Blend", "floral")
                .check(status().is(200))
        );
    }
}
