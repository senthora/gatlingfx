package com.senthora.gatlingfx.wiremock.simulations.quality;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests a quality control inspection result.
 * <p>
 * Every tea batch is carefully inspected
 * before being released for distribution.
 */
final class IssueInspectionResultScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Issue inspection result").exec(get("/quality/inspection-result")
                .check(status().is(200))
                .check(bodyString().is("APPROVED"))
                .check(header("X-Inspection-Status").is("passed"))
        );
    }
}
