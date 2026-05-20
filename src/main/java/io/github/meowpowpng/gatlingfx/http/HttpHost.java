package io.github.meowpowpng.gatlingfx.http;

import java.util.Objects;

/**
 * Represents an immutable HTTP host.
 */
public record HttpHost(String value) {

    public static HttpHost LOCALHOST = HttpHost.of("localhost");

    /**
     * Creates a new HTTP host.
     *
     * @param value host value
     *
     * @throws NullPointerException if {@code value} is null
     * @throws IllegalArgumentException if {@code value} is blank
     */
    public HttpHost {
        Objects.requireNonNull(value, "value must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("value must not be blank");
        }
    }

    /**
     * Creates a new HTTP host.
     *
     * @param value host value
     *
     * @throws NullPointerException if {@code value} is null
     * @throws IllegalArgumentException if {@code value} is blank
     */
    public static HttpHost of(String value) {
        return new HttpHost(value);
    }
}
