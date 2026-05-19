package io.github.meowpowpng.gatlingfx.core;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

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

    private String baseUrl;
    private boolean followRedirects = true;

    private SimulationProtocol() {
    }

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
     */
    public SimulationProtocol baseUrl(String baseUrl) {
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
     */
    public SimulationProtocol header(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    /**
     * Enables or disables automatic redirect following.
     *
     * @param enabled whether redirects should be followed
     *
     * @return current protocol configuration
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
