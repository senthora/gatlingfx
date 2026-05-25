package com.senthora.gatlingfx.runtime.core.internal.support;

import com.senthora.gatlingfx.support.MockWebServerTest;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public final class SuccessfulSimulation extends Simulation {

    public SuccessfulSimulation() {
        var baseUrl = MockWebServerTest.baseUrl();
        var scenario = scenario("success").exec(http("request")
                        .get("/")
                        .check(status().is(200)));

        setUp(scenario.injectOpen(atOnceUsers(1)))
                .protocols(http.baseUrl(baseUrl.asString()))
                .assertions(global().failedRequests().count().is(0L));
    }
}
