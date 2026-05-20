package com.senthora.gatlingfx.http;

import io.gatling.javaapi.core.CheckBuilder;

import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * Collection of reusable HTTP status response checks.
 */
public final class StatusChecks {

    private StatusChecks() {}

    /**
     * Asserts that the response status is HTTP 200.
     */
    public static ResponseCheck statusIsOk() {
        return () -> new CheckBuilder[]{
                status().is(200)
        };
    }

    /**
     * Asserts that the response status is HTTP 403.
     */
    public static ResponseCheck statusIsForbidden() {
        return () -> new CheckBuilder[]{
                status().is(403)
        };
    }

    /**
     * Asserts that the response status is HTTP 404.
     */
    public static ResponseCheck statusIsNotFound() {
        return () -> new CheckBuilder[]{
                status().is(404)
        };
    }

    /**
     * Asserts that the response status is HTTP 308.
     */
    public static ResponseCheck statusIsRedirect() {
        return () -> new CheckBuilder[]{
                status().is(308)
        };
    }
}
