package com.senthora.gatlingfx.runtime.core.internal.support;

import com.senthora.gatlingfx.support.MockWebServerTest;

import io.gatling.javaapi.core.Simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public final class FailedSimulation extends Simulation {

    public FailedSimulation() {
        var baseUrl = MockWebServerTest.baseUrl();
        var scenario = scenario("failure").exec(http("request")
                .get("/")
                .check(status().is(500)));

        setUp(scenario.injectOpen(atOnceUsers(1)))
                .protocols(http.baseUrl(baseUrl.value()))
                .assertions(global().failedRequests().count().is(0L));
    }
}
