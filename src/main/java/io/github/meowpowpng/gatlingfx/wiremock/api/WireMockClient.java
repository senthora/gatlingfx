package io.github.meowpowpng.gatlingfx.wiremock.api;

import io.github.meowpowpng.gatlingfx.core.BackendClient;
import io.github.meowpowpng.gatlingfx.http.HttpBaseUrl;
import io.github.meowpowpng.gatlingfx.wiremock.internal.DefaultWireMockClient;

/**
 * Backend client backed by WireMock.
 */
public interface WireMockClient extends BackendClient {

    /**
     * Creates a new WireMock client instance.
     *
     * @param baseUrl WireMock server base URL
     */
    static WireMockClient create(HttpBaseUrl baseUrl) {
        return new DefaultWireMockClient(baseUrl);
    }

    /**
     * Creates a new WireMock client instance.
     * <p>
     * The provided default stub is applied during
     * {@link #setup()} before user-registered mappings.
     *
     * @param baseUrl WireMock server base URL
     * @param defaultStub default fallback stub mapping
     */
    static WireMockClient create(HttpBaseUrl baseUrl, StubMapping defaultStub) {
        return new DefaultWireMockClient(baseUrl, defaultStub);
    }

    /**
     * Registers a WireMock stub mapping.
     *
     * @throws NullPointerException if {@code mapping} is null
     */
    WireMockClient stub(StubMapping mapping);

    /**
     * Removes all registered mappings and requests.
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
