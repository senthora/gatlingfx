package io.github.meowpowpng.gatlingfx.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class XForwardedForTest {

    @Nested
    @DisplayName("of")
    class OfMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when addresses are null")
        void should_ThrowNullPointerException_when_AddressesAreNull() {
            assertThatThrownBy(() -> XForwardedFor.of((String[]) null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when address entry is null")
        void should_ThrowNullPointerException_when_AddressEntryIsNull() {
            assertThatThrownBy(() -> XForwardedFor.of("192.168.0.1", null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when address entry is blank")
        void should_ThrowIllegalArgumentException_when_AddressEntryIsBlank() {
            assertThatThrownBy(() -> XForwardedFor.of("192.168.0.1", " "))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should preserve address order when forwarding chain is created")
        void should_PreserveAddressOrder_when_ForwardingChainIsCreated() {
            var forwardedFor = XForwardedFor.of(
                    "192.168.0.1",
                    "10.0.0.1",
                    "172.16.0.1"
            );
            assertThat(forwardedFor.first()).isEqualTo("192.168.0.1");
            assertThat(forwardedFor.last()).isEqualTo("172.16.0.1");
            assertThat(forwardedFor.values()).containsExactly(
                    "192.168.0.1",
                    "10.0.0.1",
                    "172.16.0.1"
            );
        }
    }

    @Nested
    @DisplayName("withIp")
    class WithIpMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when IP is null")
        void should_ThrowNullPointerException_when_IpIsNull() {
            assertThatThrownBy(() -> XForwardedFor.builder().withIp(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when IP is blank")
        void should_ThrowIllegalArgumentException_when_IpIsBlank() {
            assertThatThrownBy(() -> XForwardedFor.builder().withIp(" "))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("toHeaderValue")
    class ToHeaderValueMethodTests {

        @Test
        @DisplayName("Should return serialized header value when header value is requested")
        void should_ReturnSerializedHeaderValue_when_HeaderValueIsRequested() {
            var forwardedFor = XForwardedFor.of(
                    "192.168.0.1",
                    "10.0.0.1",
                    "172.16.0.1"
            );
            var expected = "192.168.0.1, 10.0.0.1, 172.16.0.1";
            assertThat(forwardedFor.toHeaderValue()).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("values")
    class ValuesMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should return immutable values view when values are requested")
        void should_ReturnImmutableValuesView_when_ValuesAreRequested() {
            var forwardedFor = XForwardedFor.of("192.168.0.1", "10.0.0.1");

            assertThatThrownBy(() -> forwardedFor.values().add("172.16.0.1"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
