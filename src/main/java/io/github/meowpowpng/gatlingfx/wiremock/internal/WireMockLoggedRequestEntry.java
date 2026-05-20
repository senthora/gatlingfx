package io.github.meowpowpng.gatlingfx.wiremock.internal;

import io.github.meowpowpng.gatlingfx.wiremock.api.LoggedRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Single logged request entry returned by WireMock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
record WireMockLoggedRequestEntry(LoggedRequest request) {}
