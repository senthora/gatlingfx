package io.github.meowpowpng.gatlingfx.core;

import org.jspecify.annotations.Nullable;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static io.gatling.javaapi.http.HttpDsl.http;

/**
 * Fluent builder for configuring HTTP
 * protocol settings used by Gatling simulations.
 * <p>
 * This abstraction provides a lightweight way to construct
 * {@link HttpProtocolBuilder} instances without coupling
 * simulations directly to low-level Gatling DSL.
 */
public final class SimulationProtocol {

    private final Map<String, String> headers = new LinkedHashMap<>();

    private @Nullable String baseUrl;
    private boolean followRedirects = true;

    private SimulationProtocol() {}

    /**
     * Creates a new protocol configuration instance.
     *
     * @return new protocol configuration
     */
    public static SimulationProtocol create() {
        return new SimulationProtocol();
    }

    /**
     * Configures the protocol base URL.
     *
     * @param baseUrl target base URL
     *
     * @return current protocol configuration
     * @throws NullPointerException if {@code baseUrl} is null
     * @throws IllegalArgumentException if {@code baseUrl} is blank
     */
    public SimulationProtocol baseUrl(String baseUrl) {
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");
        if (baseUrl.isBlank()) {
            throw new IllegalArgumentException("baseUrl must not be blank");
        }
        this.baseUrl = baseUrl;
        return this;
    }

    /**
     * Adds a default request header.
     *
     * @param name header name
     * @param value header value
     *
     * @return current protocol configuration
     *
     * @throws NullPointerException if {@code name} or {@code value} is null
     * @throws IllegalArgumentException if {@code name} is blank
     */
    public SimulationProtocol header(String name, String value) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(value, "value must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this.headers.put(name, value);
        return this;
    }

    /**
     * Enables or disables automatic redirect following.
     *
     * @param enabled whether redirects should be followed
     *
     * @return current protocol configuration
     * @throws IllegalStateException if base URL was not configured
     */
    public SimulationProtocol followRedirects(boolean enabled) {
        this.followRedirects = enabled;
        return this;
    }

    /**
     * Builds the configured Gatling HTTP protocol definition.
     *
     * @return configured HTTP protocol builder
     */
    HttpProtocolBuilder build() {
        if (baseUrl == null) {
            throw new IllegalStateException("baseUrl must be configured");
        }
        var builder = http.baseUrl(baseUrl);

        for (var entry : headers.entrySet()) {
            builder = builder.header(entry.getKey(), entry.getValue());
        }
        if (!followRedirects) {
            builder = builder.disableFollowRedirect();
        }
        return builder;
    }
}
