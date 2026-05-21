package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.SimpleHttpClient;
import com.senthora.gatlingfx.wiremock.api.LoggedRequests;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.WireMockClient;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default {@link WireMockClient} implementation.
 * <p>
 * <strong>Thread Safety:</strong>
 * This implementation maintains mutable stub state
 * and is intended to be configured and used from
 * a single orchestration thread during simulation setup.
 */
public final class DefaultWireMockClient implements WireMockClient {

    private static final String RESET_PATH = "/__admin/reset";
    private static final String MAPPINGS_PATH = "/__admin/mappings";
    private static final String REQUESTS_PATH = "/__admin/requests";

    private final List<StubMapping> mappings = new ArrayList<>();

    private final SimpleHttpClient httpClient;
    private final @Nullable StubMapping defaultStub;

    public DefaultWireMockClient(SimpleHttpClient httpClient, StubMapping defaultStub) {
        Objects.requireNonNull(httpClient, "httpClient must not be null");
        Objects.requireNonNull(defaultStub, "default stub must not be null");

        this.httpClient = httpClient;
        this.defaultStub = defaultStub;
    }

    public DefaultWireMockClient(SimpleHttpClient httpClient) {
        Objects.requireNonNull(httpClient, "httpClient must not be null");

        this.httpClient = httpClient;
        this.defaultStub = null;
    }

    @Override
    public void setup() {
        if (defaultStub != null) {
            applyStub(defaultStub);
        }
        mappings.forEach(this::applyStub);
    }

    @Override
    public void teardown() {
        reset();
    }

    @Override
    public DefaultWireMockClient stub(StubMapping mapping) {
        Objects.requireNonNull(mapping, "mapping must not be null");
        mappings.add(mapping);
        return this;
    }

    @Override
    public void reset() {
        httpClient.post(RESET_PATH);
    }

    @Override
    public LoggedRequests requests() {
        var response = httpClient.get(REQUESTS_PATH).body();
        return WireMockMapper.parseRequests(response);
    }

    @Override
    public HttpBaseUrl baseUrl() {
        return httpClient.baseUrl();
    }

    private void applyStub(StubMapping mapping) {
        var stub = WireMockMapper.toStub(mapping);
        var payload = WireMockCodec.serialize(stub);

        httpClient.post(MAPPINGS_PATH, payload);
    }
}
