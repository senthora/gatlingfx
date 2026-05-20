package com.senthora.gatlingfx.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHostTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when value is null")
    void should_ThrowNullPointerException_when_ValueIsNull() {
        assertThatThrownBy(() -> new HttpHost(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is blank")
    void should_ThrowIllegalArgumentException_when_ValueIsBlank() {
        assertThatThrownBy(() -> new HttpHost(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should create host from provided value")
    void should_CreateHost_when_ValidValueIsProvided() {
        var host = "example.com";

        assertThat(HttpHost.of(host)).isEqualTo(new HttpHost(host));
    }
}
