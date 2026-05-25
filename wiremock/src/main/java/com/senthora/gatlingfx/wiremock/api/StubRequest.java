package com.senthora.gatlingfx.wiremock.api;

import com.senthora.gatlingfx.http.api.HttpHeader;
import com.senthora.gatlingfx.http.api.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a request matcher for a WireMock stub.
 */
public record StubRequest(HttpMethod method, UrlMatcher url, List<HttpHeader> headers) {

    /**
     * Creates a new stub request matcher.
     *
     * @param method request method
     * @param url URL matcher
     * @param headers required request headers
     *
     * @throws NullPointerException if any argument or header is null
     */
    public StubRequest {
        Objects.requireNonNull(method, "method must not be null");
        Objects.requireNonNull(url, "url matcher must not be null");
        Objects.requireNonNull(headers, "headers must not be null");

        for (HttpHeader header : headers) {
            Objects.requireNonNull(header, "header must not be null");
        }
        headers = List.copyOf(headers);
    }

    /**
     * Defines a WireMock URL matcher.
     */
    public sealed interface UrlMatcher permits ExactUrl, UrlPattern {}

    /**
     * Creates a request matcher for any method and URL.
     *
     * @return request matcher
     */
    public static StubRequest any() {
        return requestMatching(HttpMethod.ANY, ".*");
    }

    /**
     * Creates a request matcher for the given method and path.
     *
     * @param method HTTP request method
     * @param path exact request path
     *
     * @return request matcher
     */
    public static StubRequest request(HttpMethod method, String path) {
        return new StubRequest(method, new ExactUrl(path), List.of());
    }

    /**
     * Creates a request matcher for the given method and URL pattern.
     *
     * @param method HTTP request method
     * @param pattern request URL pattern
     *
     * @return request matcher
     */
    public static StubRequest requestMatching(HttpMethod method, String pattern) {
        return new StubRequest(method, new UrlPattern(pattern), List.of());
    }

    /**
     * Adds a required request header matcher.
     *
     * @param header required request header
     *
     * @return request matcher
     *
     * @throws NullPointerException if {@code header} is null
     */
    public StubRequest withHeader(HttpHeader header) {
        Objects.requireNonNull(header, "header must not be null");

        var headers = new ArrayList<>(this.headers);
        headers.add(header);

        return new StubRequest(method, url, headers);
    }

    /**
     * Creates a stub returning the given status.
     */
    public StubMapping willReturn(int status) {
        var response = new StubResponse(status, "", List.of());
        return new StubMapping(this, response);
    }

    /**
     * Creates a JSON stub response.
     */
    public StubMapping willReturnJson(int status, String body) {
        var header = HttpHeader.of("Content-Type", "application/json");
        var response = new StubResponse(status, body, List.of(header));

        return new StubMapping(this, response);
    }

    /**
     * Creates a plain text stub response.
     */
    public StubMapping willReturnText(int status, String body) {
        var header = HttpHeader.of("Content-Type", "text/plain");
        var response = new StubResponse(status, body, List.of(header));

        return new StubMapping(this, response);
    }

    /**
     * Matches an exact request URL.
     */
    public record ExactUrl(String value) implements UrlMatcher {

        /**
         * Creates an exact URL matcher.
         *
         * @throws NullPointerException if {@code value} is null
         * @throws IllegalArgumentException if {@code value} is blank
         */
        public ExactUrl {
            Objects.requireNonNull(value, "value must not be null");
            if (value.isBlank()) {
                throw new IllegalArgumentException("value must not be blank");
            }
        }
    }

    /**
     * Matches a request URL pattern.
     */
    public record UrlPattern(String value) implements UrlMatcher {

        /**
         * Creates a URL pattern matcher.
         *
         * @throws NullPointerException if {@code value} is null
         * @throws IllegalArgumentException if {@code value} is blank
         */
        public UrlPattern {
            Objects.requireNonNull(value, "value must not be null");
            if (value.isBlank()) {
                throw new IllegalArgumentException("value must not be blank");
            }
        }
    }
}
