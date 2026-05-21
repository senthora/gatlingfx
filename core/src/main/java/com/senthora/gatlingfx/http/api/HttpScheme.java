package com.senthora.gatlingfx.http.api;

/**
 * Supported HTTP URI schemes.
 */
public enum HttpScheme {
    HTTP("http"),
    HTTPS("https");

    private final String value;

    HttpScheme(String value) {
        this.value = value;
    }

    /**
     * Returns the raw scheme value.
     */
    public String value() {
        return value;
    }
}
