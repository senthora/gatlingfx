package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.http.RequestHeader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Request recorded by WireMock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record LoggedRequest(String url, Map<String, String> headers) {

    /**
     * Creates a new logged request.
     *
     * @param url request URL
     * @param headers request headers
     *
     * @throws NullPointerException if {@code url},
     * {@code headers}, any header name, or value is null
     */
    public LoggedRequest {
        Objects.requireNonNull(url, "url must not be null");
        Objects.requireNonNull(headers, "headers must not be null");

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Objects.requireNonNull(entry.getKey(), "header name must not be null");
            Objects.requireNonNull(entry.getValue(), "header value must not be null");
        }
        headers = Map.copyOf(headers);
    }

    /**
     * Returns the value of the given request header.
     *
     * @param header request header
     *
     * @return optional containing the first matching header value,
     * or an empty optional if no matching header exists
     */
    public Optional<String> header(RequestHeader header) {
        return headers.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(header.headerName()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
