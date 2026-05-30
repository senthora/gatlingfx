package com.senthora.gatlingfx.wiremock.simulations.quality;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests a warehouse label for a tea batch.
 * <p>
 * The warehouse prefers labels that can
 * be read without specialized equipment.
 */
final class IssueWarehouseLabelScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Issue warehouse label").exec(get("/quality/warehouse-label")
                .check(status().is(200))
                .check(bodyString().is("BATCH-42"))
                .check(header("Content-Type").is("text/plain"))
        );
    }
}
