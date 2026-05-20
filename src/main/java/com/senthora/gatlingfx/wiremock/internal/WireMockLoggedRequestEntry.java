package com.senthora.gatlingfx.wiremock.internal;

import com.senthora.gatlingfx.wiremock.api.LoggedRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Single logged request entry returned by WireMock.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
record WireMockLoggedRequestEntry(LoggedRequest request) {}
