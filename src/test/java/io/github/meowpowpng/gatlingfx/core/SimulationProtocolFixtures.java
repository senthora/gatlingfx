package io.github.meowpowpng.gatlingfx.core;

import io.github.meowpowpng.gatlingfx.http.HttpBaseUrl;
import io.github.meowpowpng.gatlingfx.http.HttpHost;
import io.github.meowpowpng.gatlingfx.http.HttpScheme;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import org.mockito.Mockito;

final class SimulationProtocolFixtures {

    static final HttpBaseUrl EXAMPLE_URL = HttpBaseUrl.of(
            HttpScheme.HTTPS,
            HttpHost.of("example.com")
    );

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
