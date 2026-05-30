package com.senthora.gatlingfx.wiremock.simulations.runs;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests preparation of the factory house blend.
 * <p>
 * When no recipe can be found, workers fall back
 * to whatever the factory happened to prepare first.
 */
final class PrepareHouseBlendScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Prepare house blend").exec(get("/recipes/house")
                .check(status().is(200))
                .check(bodyString().is("homemade"))
        );
    }
}
