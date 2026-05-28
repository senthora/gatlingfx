package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.http.api.NetworkAddress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NetworkAddressTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when host is null")
        void should_ThrowNullPointerException_when_HostIsNull() {
            assertThatThrownBy(() -> new NetworkAddress(null, 8080))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when host is blank")
        void should_ThrowIllegalArgumentException_when_HostIsBlank() {
            assertThatThrownBy(() -> new NetworkAddress("", 8080))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when port is not positive")
        void should_ThrowIllegalArgumentException_when_PortIsNotPositive() {
            assertThatThrownBy(() -> NetworkAddress.localhost(0))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should return provided host and port when network address is created")
        void should_ReturnProvidedHostAndPort_when_NetworkAddressIsCreated() {
            var address = NetworkAddress.localhost(8080);

            assertThat(address.host()).isEqualTo("localhost");
            assertThat(address.port()).isEqualTo(8080);
        }
    }

    @Nested
    @DisplayName("of")
    class OfMethodTests {

        @Test
        @DisplayName("Should return network address when arguments are valid")
        void should_ReturnNetworkAddress_when_ArgumentsAreValid() {
            var address = NetworkAddress.of("example.com", 8080);

            assertThat(address.host()).isEqualTo("example.com");
            assertThat(address.port()).isEqualTo(8080);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when host is null")
        void should_ThrowNullPointerException_when_HostIsNull() {
            assertThatThrownBy(() -> NetworkAddress.of(null, 8080))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when host is blank")
        void should_ThrowIllegalArgumentException_when_HostIsBlank() {
            assertThatThrownBy(() -> NetworkAddress.of("", 8080))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when port is not positive")
        void should_ThrowIllegalArgumentException_when_PortIsNotPositive() {
            assertThatThrownBy(() -> NetworkAddress.of("example.com", 0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("localhost")
    class LocalhostMethodTests {

        @Test
        @DisplayName("Should return localhost network address when arguments are valid")
        void should_ReturnLocalhostNetworkAddress_when_ArgumentsAreValid() {
            var address = NetworkAddress.localhost(8080);

            assertThat(address.host()).isEqualTo("localhost");
            assertThat(address.port()).isEqualTo(8080);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when port is not positive")
        void should_ThrowIllegalArgumentException_when_PortIsNotPositive() {
            assertThatThrownBy(() -> NetworkAddress.localhost(0))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
