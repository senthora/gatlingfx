package com.senthora.gatlingfx.simulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationProtocolTest {

    @Nested
    @DisplayName("baseUrl")
    class BaseUrlMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when base URL is null")
        void should_ThrowNullPointerException_when_BaseUrlIsNull() {
            assertThatThrownBy(() -> SimulationProtocol.create().baseUrl(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should use configured base URL when protocol is built")
        void should_UseConfiguredBaseUrl_when_ProtocolIsBuilt() {
            var builder = SimulationProtocolFixtures.mockBuilder();
            var factory = SimulationProtocolFixtures.mockFactory(builder);

            SimulationProtocolFixtures.protocol(factory).build();

            Mockito.verify(factory).baseUrl(SimulationProtocolFixtures.EXAMPLE_URL);
        }
    }

    @Nested
    @DisplayName("header")
    class HeaderMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when header name is null")
        void should_ThrowNullPointerException_when_HeaderNameIsNull() {
            var protocol = SimulationProtocol.create();

            assertThatThrownBy(() -> protocol.header(null, "application/json"))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when header value is null")
        void should_ThrowNullPointerException_when_HeaderValueIsNull() {
            var protocol = SimulationProtocol.create();

            assertThatThrownBy(() -> protocol.header("Content-Type", null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when header name is blank")
        void should_ThrowIllegalArgumentException_when_HeaderNameIsBlank() {
            var protocol = SimulationProtocol.create();

            assertThatThrownBy(() -> protocol.header(" ", "application/json"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should use configured header when protocol is built")
        void should_UseConfiguredHeader_when_ProtocolIsBuilt() {
            var builder = SimulationProtocolFixtures.mockBuilder();
            var factory = SimulationProtocolFixtures.mockFactory(builder);

            SimulationProtocolFixtures.protocol(factory)
                    .header("Content-Type", "application/json")
                    .build();

            Mockito.verify(builder).header("Content-Type", "application/json");
        }
    }

    @Nested
    @DisplayName("followRedirects")
    class FollowRedirectsMethodTests {

        @Test
        @DisplayName("Should disable redirect following when follow redirects is disabled")
        void should_DisableRedirectFollowing_when_FollowRedirectsIsDisabled() {
            var builder = SimulationProtocolFixtures.mockBuilder();
            var factory = SimulationProtocolFixtures.mockFactory(builder);

            SimulationProtocolFixtures.protocol(factory)
                    .followRedirects(false)
                    .build();

            Mockito.verify(builder).disableFollowRedirect();
        }
    }

    @Nested
    @DisplayName("build")
    class BuildMethodTests {

        @Test
        @DisplayName("Should throw IllegalStateException when base URL is not configured")
        void should_ThrowIllegalStateException_when_BaseUrlIsNotConfigured() {
            assertThatThrownBy(SimulationProtocol.create()::build)
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
