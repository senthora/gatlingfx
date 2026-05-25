package com.senthora.gatlingfx.http.api;

import java.net.URI;
import java.util.Objects;

/**
 * Represents an immutable HTTP base URL.
 */
public record HttpBaseUrl(HttpScheme scheme, NetworkAddress address) {

    public static final HttpBaseUrl LOCALHOST =
            HttpBaseUrl.of(HttpScheme.HTTP, HttpHost.LOCALHOST);

    /**
     * Creates a new HTTP base URL.
     *
     * @param scheme HTTP URI scheme
     * @param address network address
     *
     * @throws NullPointerException if {@code scheme} or {@code address} is null
     */
    public HttpBaseUrl {
        Objects.requireNonNull(scheme, "scheme must not be null");
        Objects.requireNonNull(address, "address must not be null");
    }

    /**
     * Creates a new HTTP base URL.
     *
     * @param scheme HTTP URI scheme
     * @param address network address
     *
     * @throws NullPointerException if {@code scheme} or {@code address} is null
     */
    public static HttpBaseUrl of(HttpScheme scheme, NetworkAddress address) {
        return new HttpBaseUrl(scheme, address);
    }

    /**
     * Creates a new HTTP base URL using
     * the default port for provided scheme.
     *
     * @param scheme HTTP URI scheme
     * @param host HTTP host
     *
     * @throws NullPointerException if {@code scheme} or {@code host} is null
     */
    public static HttpBaseUrl of(HttpScheme scheme, HttpHost host) {
        var port = switch (scheme) {
            case HTTP -> 80;
            case HTTPS -> 443;
        };
        var address = new NetworkAddress(host, port);
        return new HttpBaseUrl(scheme, address);
    }

    /**
     * Creates a URI for the provided path.
     * <p>
     * <strong>API Note:</strong>
     * The provided path is normalized and
     * does not need to start with {@code /}.
     *
     * @param path request path
     */
    public URI uri(String path) {
        if (!path.startsWith("/")) {
            path = '/' + path;
        }
        return URI.create(toString()).resolve(path);
    }

    /**
     * Returns the base URL in
     * {@code scheme://host:port} format.
     */
    public String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return scheme.value() + "://" + address.value();
    }
}
