package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.http.api.HttpHost;
import com.senthora.gatlingfx.http.api.NetworkAddress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NetworkAddressTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when host is null")
    void should_ThrowNullPointerException_when_HostIsNull() {
        assertThatThrownBy(() -> new NetworkAddress(null, 8080))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when port is not positive")
    void should_ThrowIllegalArgumentException_when_PortIsNotPositive() {
        assertThatThrownBy(() -> new NetworkAddress(HttpHost.LOCALHOST, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should return provided host and port when network address is created")
    void should_ReturnProvidedHostAndPort_when_NetworkAddressIsCreated() {
        var address = new NetworkAddress(HttpHost.LOCALHOST, 8080);

        assertThat(address.host()).isEqualTo(HttpHost.LOCALHOST);
        assertThat(address.port()).isEqualTo(8080);
    }

    @Test
    @DisplayName("Should return formatted address when value is requested")
    void should_ReturnFormattedAddress_when_ValueIsRequested() {
        var address = new NetworkAddress(HttpHost.LOCALHOST, 8080);

        assertThat(address.value()).isEqualTo("localhost:8080");
    }

    @Test
    @DisplayName("Should return network address with provided host and port when arguments are valid")
    void should_ReturnNetworkAddressWithProvidedHostAndPort_when_ArgumentsAreValid() {
        var address = new NetworkAddress(HttpHost.LOCALHOST, 8080);

        assertThat(address.host()).isEqualTo(HttpHost.LOCALHOST);
        assertThat(address.port()).isEqualTo(8080);
    }
}
