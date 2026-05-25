package com.senthora.gatlingfx.http.api;

import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of an
 * {@code X-Forwarded-For} header chain.
 */
public record XForwardedFor(List<String> chain) {

    public static final String HEADER_NAME = "X-Forwarded-For";

    public XForwardedFor(List<String> chain) {
        Objects.requireNonNull(chain, "chain must not be null");

        for (String value : chain) {
            Objects.requireNonNull(value, "chain entry must not be null");
            if (value.isBlank()) {
                throw new IllegalArgumentException("chain entry must not be blank");
            }
        }
        this.chain = List.copyOf(chain);
    }

    /**
     * Creates forwarding chain from provided addresses.
     * <p>
     * <strong>API Note:</strong>
     * Addresses are preserved in insertion order.
     *
     * @param addresses forwarding chain addresses
     *
     * @throws NullPointerException if {@code addresses} or any entry is null
     * @throws IllegalArgumentException if any address is blank
     */
    public static XForwardedFor of(String... addresses) {
        Objects.requireNonNull(addresses, "addresses must not be null");
        return new XForwardedFor(List.of(addresses));
    }

    /**
     * Returns the first address in the forwarding chain.
     */
    public String first() {
        return chain.getFirst();
    }

    /**
     * Returns the last address in the forwarding chain.
     */
    public String last() {
        return chain.getLast();
    }

    /**
     * Serializes into {@link HttpHeader}.
     */
    public HttpHeader toHttpHeader() {
        return HttpHeader.of(HEADER_NAME, String.join(", ", chain));
    }
}
