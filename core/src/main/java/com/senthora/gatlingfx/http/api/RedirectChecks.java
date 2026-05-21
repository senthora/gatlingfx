package com.senthora.gatlingfx.http.api;

import io.gatling.javaapi.core.CheckBuilder;

import static io.gatling.javaapi.http.HttpDsl.header;

/**
 * Collection of reusable HTTP redirect response checks.
 */
public final class RedirectChecks {

    private RedirectChecks() {}

    /**
     * Asserts that the {@code Location} header
     * redirects to the provided path.
     *
     * @param path redirect path
     */
    public static ResponseCheck locationIsRedirectTo(String path) {
        return () -> new CheckBuilder[]{
                header("Location").is(path)
        };
    }
}
