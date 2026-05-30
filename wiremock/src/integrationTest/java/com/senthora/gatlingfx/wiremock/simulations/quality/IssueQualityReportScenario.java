package com.senthora.gatlingfx.wiremock.simulations.quality;

import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.bodyString;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.header;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Requests a quality control report.
 * <p>
 * The quality department insists on filing
 * every inspection result as structured data.
 */
final class IssueQualityReportScenario implements SimulationScenario {

    @Override
    public ScenarioBuilder build() {
        return scenario("Issue quality report").exec(get("/quality/report")
                .check(status().is(200))
                .check(bodyString().is("{\"result\":\"approved\"}"))
                .check(header("Content-Type").is("application/json"))
        );
    }
}
