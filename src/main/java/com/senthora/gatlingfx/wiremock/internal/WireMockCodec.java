package com.senthora.gatlingfx.wiremock.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * JSON serialization and deserialization
 * utilities for WireMock payloads.
 */
final class WireMockCodec {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private WireMockCodec() {}

    /**
     * Serializes a WireMock stub payload to JSON.
     *
     * @param stub WireMock stub payload
     *
     * @return serialized JSON payload
     *
     * @throws NullPointerException if {@code stub} is null
     * @throws WireMockCodecException if serialization fails
     */
    static String serialize(WireMockStub stub) {
        Objects.requireNonNull(stub, "json must not be null");
        try {
            return MAPPER.writeValueAsString(stub);
        }
        catch (JsonProcessingException e) {
            throw new WireMockCodecException("Failed to serialize WireMock stub", e);
        }
    }

    /**
     * Deserializes WireMock logged requests from JSON.
     *
     * @param json serialized JSON payload
     *
     * @return deserialized logged requests payload
     *
     * @throws NullPointerException if {@code json} is null
     * @throws WireMockCodecException if deserialization fails
     */
    static WireMockLoggedRequests deserialize(String json) {
        Objects.requireNonNull(json, "json must not be null");
        try {
            return MAPPER.readValue(json, WireMockLoggedRequests.class);
        }
        catch (JsonProcessingException e) {
            throw new WireMockCodecException("Failed to deserialize WireMock logged requests", e);
        }
    }
}
