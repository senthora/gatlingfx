package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.http.HttpHost;
import com.senthora.gatlingfx.http.NetworkAddress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProxyServerTest {

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when address is null")
    void should_ThrowNullPointerException_when_AddressIsNull() {
        assertThatThrownBy(() -> new ProxyServer(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Should return provided address when proxy server is created")
    void should_ReturnProvidedAddress_when_ProxyServerIsCreated() {
        var address = new NetworkAddress(HttpHost.LOCALHOST, 8080);
        var proxyServer = new ProxyServer(address);

        assertThat(proxyServer.address()).isEqualTo(address);
    }

    @Test
    @DisplayName("Should return proxy server with provided address when address is valid")
    void should_ReturnProxyServerWithProvidedAddress_when_AddressIsValid() {
        var address = new NetworkAddress(HttpHost.LOCALHOST, 8080);
        var proxyServer = ProxyServer.of(address);

        assertThat(proxyServer.address()).isEqualTo(address);
    }
}
