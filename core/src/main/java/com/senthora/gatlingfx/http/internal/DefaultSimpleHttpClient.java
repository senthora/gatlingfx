package com.senthora.gatlingfx.http.internal;

import com.senthora.gatlingfx.http.api.SimpleHttpClient;
import com.senthora.gatlingfx.http.api.HttpBaseUrl;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 * Default {@link SimpleHttpClient} implementation.
 */
public final class DefaultSimpleHttpClient implements SimpleHttpClient {

    private final HttpClient client = HttpClient.newHttpClient();

    private final HttpBaseUrl baseUrl;

    /**
     * Creates a new HTTP client instance.
     *
     * @param baseUrl backend base URL
     */
    public DefaultSimpleHttpClient(HttpBaseUrl baseUrl) {
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");
        this.baseUrl = baseUrl;
    }

    @Override
    public HttpResponse<String> get(String path) {
        Objects.requireNonNull(path, "path must not be null");

        var request = HttpRequest.newBuilder()
                .uri(baseUrl.uri(path))
                .GET()
                .build();

        return send(request, "GET");
    }

    @Override
    public HttpResponse<String> post(String path) {
        return post(path, HttpRequest.BodyPublishers.noBody());
    }

    @Override
    public HttpResponse<String> post(String path, String body) {
        Objects.requireNonNull(body, "body must not be null");

        return post(path, HttpRequest.BodyPublishers.ofString(body));
    }

    @Override
    public HttpBaseUrl baseUrl() {
        return baseUrl;
    }

    private HttpResponse<String> send(HttpRequest request, String method) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException e) {
            throw HttpClientNetworkException.requestFailed(method, e);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw HttpClientNetworkException.requestInterrupted(method, e);
        }
    }

    private HttpResponse<String> post(String path, HttpRequest.BodyPublisher body) {
        Objects.requireNonNull(path, "path must not be null");

        var builder = HttpRequest.newBuilder()
                .uri(baseUrl.uri(path))
                .POST(body);

        if (body.contentLength() > 0) {
            builder.header("Content-Type", "application/json");
        }
        var request = builder.build();
        return send(request, "POST");
    }
}
