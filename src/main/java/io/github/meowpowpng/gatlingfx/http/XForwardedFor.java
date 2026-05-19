package io.github.meowpowpng.gatlingfx.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of an
 * {@code X-Forwarded-For} header chain.
 */
public final class XForwardedFor {

    private final List<String> chain;

    private XForwardedFor(List<String> chain) {
        this.chain = List.copyOf(chain);
    }

    /**
     * Creates a new forwarding chain builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates forwarding chain from provided addresses.
     *
     * @param addresses forwarding chain addresses
     */
    public static XForwardedFor of(String... addresses) {
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
     * Returns immutable forwarding chain view.
     */
    public List<String> values() {
        return chain;
    }

    /**
     * Serializes the forwarding chain
     * into HTTP header format.
     */
    public String toHeaderValue() {
        return String.join(", ", chain);
    }

    /**
     * Fluent builder for constructing forwarding chains.
     */
    public static final class Builder {

        private final List<String> chain = new ArrayList<>();

        /**
         * Appends IP address to the forwarding chain.
         *
         * @param ip IP address
         */
        public Builder withIp(String ip) {
            chain.add(ip);
            return this;
        }

        /**
         * Builds immutable forwarding chain.
         */
        public XForwardedFor build() {
            return new XForwardedFor(chain);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof XForwardedFor that)) {
            return false;
        }
        return chain.equals(that.chain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chain);
    }

    @Override
    public String toString() {
        return toHeaderValue();
    }
}
