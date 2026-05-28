package com.senthora.gatlingfx.simulation.api;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;

import com.senthora.gatlingfx.simulation.internal.HttpProtocolFactory;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import org.mockito.Mockito;

final class SimulationProtocolFixtures {

    static final HttpBaseUrl EXAMPLE_URL =
            HttpBaseUrl.of(HttpScheme.HTTPS, "example.com");

    private SimulationProtocolFixtures() {}

    static HttpProtocolBuilder mockBuilder() {
        return Mockito.mock(HttpProtocolBuilder.class);
    }

    static HttpProtocolFactory mockFactory(HttpProtocolBuilder builder) {
        var factory = Mockito.mock(HttpProtocolFactory.class);

        Mockito.when(factory.baseUrl(EXAMPLE_URL))
                .thenReturn(builder);

        return factory;
    }

    static SimulationProtocol protocol(HttpProtocolFactory factory) {
        return new SimulationProtocol(factory).baseUrl(EXAMPLE_URL);
    }
}
