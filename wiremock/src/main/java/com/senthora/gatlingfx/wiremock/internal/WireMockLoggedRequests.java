package com.senthora.gatlingfx.wiremock.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Logged request payload returned by WireMock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
record WireMockLoggedRequests(List<WireMockLoggedRequestEntry> requests) {}
