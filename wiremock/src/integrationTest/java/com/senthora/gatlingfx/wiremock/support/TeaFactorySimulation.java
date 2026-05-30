package com.senthora.gatlingfx.wiremock.support;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpScheme;
import com.senthora.gatlingfx.http.api.NetworkAddress;

/**
 * Shared tea factory fixtures used by
 * WireMock integration simulations.
 */
public final class TeaFactorySimulation {

    /**
     * Tea factory production endpoint.
     */
    public static final HttpBaseUrl BASE_URL = HttpBaseUrl.of(
            HttpScheme.HTTP,
            NetworkAddress.localhost(8081)
    );

    private TeaFactorySimulation() {}
}
