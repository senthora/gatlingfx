package io.github.meowpowpng.gatlingfx.http;

import io.gatling.javaapi.core.CheckBuilder;

import static io.gatling.javaapi.http.HttpDsl.header;

/**
 * Collection of reusable HTTP header response checks.
 */
public final class HeaderChecks {

    private HeaderChecks() {}

    /**
     * Asserts that the provided header
     * matches the expected value.
     *
     * @param name header name
     * @param value expected header value
     */
    public static ResponseCheck headerIs(String name, String value) {
        return () -> new CheckBuilder[]{
                header(name).is(value)
        };
    }

    /**
     * Asserts that the provided header exists.
     *
     * @param name header name
     */
    public static ResponseCheck headerExists(String name) {
        return () -> new CheckBuilder[]{
                header(name).exists()
        };
    }
}
