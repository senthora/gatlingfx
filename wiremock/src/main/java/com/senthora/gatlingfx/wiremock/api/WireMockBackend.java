package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.http.api.SimpleHttpClient;
import com.senthora.gatlingfx.simulation.api.SimulationBackend;
import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.wiremock.internal.DefaultWireMockBackend;

/**
 * Simulation backend backed by WireMock.
 */
public interface WireMockBackend extends SimulationBackend {

    /**
     * Creates a new WireMock backend instance.
     *
     * @param baseUrl WireMock server base URL
     */
    static WireMockBackend create(HttpBaseUrl baseUrl) {
        var httpClient = SimpleHttpClient.create(baseUrl);
        return new DefaultWireMockBackend(httpClient);
    }

    /**
     * Creates a new WireMock backend instance.
     * <p>
     * The provided default stub is applied during
     * {@link #setup()} before user-registered mappings.
     *
     * @param baseUrl WireMock server base URL
     * @param defaultStub default fallback stub mapping
     */
    static WireMockBackend create(HttpBaseUrl baseUrl, StubMapping defaultStub) {
        var httpClient = SimpleHttpClient.create(baseUrl);
        return new DefaultWireMockBackend(httpClient, defaultStub);
    }

    /**
     * Registers a WireMock stub mapping.
     *
     * @throws NullPointerException if {@code mapping} is null
     */
    WireMockBackend stub(StubMapping mapping);

    /**
     * Resets WireMock server state.
     */
    void reset();

    /**
     * Returns all requests recorded by WireMock.
     */
    LoggedRequests requests();

    /**
     * Returns WireMock server base URL.
     */
    HttpBaseUrl baseUrl();
}
