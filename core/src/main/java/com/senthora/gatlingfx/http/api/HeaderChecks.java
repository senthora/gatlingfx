package com.senthora.gatlingfx.http.api;

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
     * @param header expected HTTP header
     *
     * @return response check
     */
    public static ResponseCheck headerIs(HttpHeader header) {
        return () -> new CheckBuilder[]{
                header(header.name()).is(header.value())
        };
    }

    /**
     * Asserts that the provided header exists.
     *
     * @param header expected HTTP header
     *
     * @return response check
     */
    public static ResponseCheck headerExists(RequestHeader header) {
        return () -> new CheckBuilder[]{
                header(header.headerName()).exists()
        };
    }
}
