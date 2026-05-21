package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.http.api.HttpBaseUrl;
import com.senthora.gatlingfx.http.api.HttpMethod;
import com.senthora.gatlingfx.http.api.RequestHeader;
import com.senthora.gatlingfx.wiremock.api.LoggedRequests;
import com.senthora.gatlingfx.wiremock.api.StubMapping;
import com.senthora.gatlingfx.wiremock.api.WireMockClient;

import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default {@link WireMockClient} implementation.
 * <p>
 * <strong>Tread Safety:</strong>
 * This implementation maintains mutable stub state
 * and is intended to be configured and used from
 * a single orchestration thread during simulation setup.
 */
public final class DefaultWireMockClient implements WireMockClient {

    private static final String RESET_PATH = "/__admin/reset";
    private static final String MAPPINGS_PATH = "/__admin/mappings";
    private static final String REQUESTS_PATH = "/__admin/requests";

    private final HttpClient client = HttpClient.newHttpClient();
    private final List<StubMapping> mappings = new ArrayList<>();

    private final HttpBaseUrl baseUrl;
    private final @Nullable StubMapping defaultStub;

    public DefaultWireMockClient(HttpBaseUrl baseUrl, StubMapping defaultStub) {
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");
        Objects.requireNonNull(defaultStub, "default stub must not be null");

        this.baseUrl = baseUrl;
        this.defaultStub = defaultStub;
    }

    public DefaultWireMockClient(HttpBaseUrl baseUrl) {
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");

        this.baseUrl = baseUrl;
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
        post(RESET_PATH);
    }

    @Override
    public LoggedRequests requests() {
        return WireMockMapper.parseRequests(get(REQUESTS_PATH));
    }

    @Override
    public HttpBaseUrl baseUrl() {
        return baseUrl;
    }

    private void applyStub(StubMapping mapping) {
        var stub = WireMockMapper.toStub(mapping);
        post(MAPPINGS_PATH, WireMockCodec.serialize(stub));
    }

    private void post(String path) {
        post(path, HttpRequest.BodyPublishers.noBody());
    }

    private void post(String path, String body) {
        post(path, HttpRequest.BodyPublishers.ofString(body));
    }

    private void post(String path, HttpRequest.BodyPublisher body) {
        var builder = HttpRequest.newBuilder()
                .uri(baseUrl.uri(path))
                .POST(body);

        if (body.contentLength() > 0) {
            builder.header(RequestHeader.CONTENT_TYPE.headerName(), "application/json");
        }
        var request = builder.build();
        var response = send(request, HttpMethod.POST);

        if (response.statusCode() >= 300) {
            throw WireMockNetworkException.requestRejected(HttpMethod.POST, response);
        }
    }

    private String get(String path) {
        var request = HttpRequest.newBuilder()
                .uri(baseUrl.uri(path))
                .GET()
                .build();

        var response = send(request, HttpMethod.GET);

        if (response.statusCode() >= 300) {
            throw WireMockNetworkException.requestRejected(HttpMethod.GET, response);
        }
        return response.body();
    }

    private HttpResponse<String> send(HttpRequest request, HttpMethod method) {
        try {
            var handler = HttpResponse.BodyHandlers.ofString();
            return client.send(request, handler);
        }
        catch (IOException e) {
            throw WireMockNetworkException.requestFailed(method, e);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw WireMockNetworkException.requestInterrupted(method, e);
        }
    }
}
