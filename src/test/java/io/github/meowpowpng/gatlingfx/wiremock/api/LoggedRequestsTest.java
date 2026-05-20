package io.github.meowpowpng.gatlingfx.wiremock.api;

import io.github.meowpowpng.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoggedRequestsTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when requests are null")
        void should_ThrowNullPointerException_when_RequestsAreNull() {
            assertThatThrownBy(() -> new LoggedRequests(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw NullPointerException when request is null")
        void should_ThrowNullPointerException_when_RequestIsNull() {
            var requests = new ArrayList<LoggedRequest>();
            requests.add(null);

            assertThatThrownBy(() -> new LoggedRequests(requests))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should store defensive copy when constructed")
        void should_StoreDefensiveCopy_when_Constructed() {
            var requests = new ArrayList<LoggedRequest>();
            requests.add(new LoggedRequest("/requests", Map.of()));

            var loggedRequests = new LoggedRequests(requests);

            requests.add(new LoggedRequest("/other", Map.of()));

            var expected = new LoggedRequest("/requests", Map.of());
            assertThat(loggedRequests.requests()).containsExactly(expected);
        }

        @Test
        @DisplayName("Should return immutable requests when requests are accessed")
        void should_ReturnImmutableRequests_when_RequestsAreAccessed() {
            var loggedRequests = new LoggedRequests(List.of(
                    new LoggedRequest("/requests", Map.of())
            ));
            assertThatThrownBy(() -> loggedRequests.requests()
                    .add(new LoggedRequest("/other", Map.of())))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("lastFor")
    class LastForMethodTests {

        @Test
        @DisplayName("Should return last matching request when path exists")
        void should_ReturnLastMatchingRequest_when_PathExists() {
            var firstRequest = new LoggedRequest("/requests", Map.of());
            var lastRequest = new LoggedRequest(
                    "/requests",
                    TestHeaders.authorizationHeaders()
            );
            var loggedRequests = new LoggedRequests(List.of(
                    firstRequest,
                    new LoggedRequest("/other", Map.of()),
                    lastRequest
            ));
            var actualRequest = loggedRequests.lastFor("/requests");

            assertThat(actualRequest).contains(lastRequest);
        }

        @Test
        @DisplayName("Should return empty optional when path does not exist")
        void should_ReturnEmptyOptional_when_PathDoesNotExist() {
            var loggedRequests = new LoggedRequests(List.of(
                    new LoggedRequest("/requests", Map.of())
            ));
            assertThat(loggedRequests.lastFor("/other")).isEmpty();
        }
    }

    @Nested
    @DisplayName("isEmpty")
    class IsEmptyMethodTests {

        @Test
        @DisplayName("Should return true when requests are empty")
        void should_ReturnTrue_when_RequestsAreEmpty() {
            var loggedRequests = new LoggedRequests(List.of());

            assertThat(loggedRequests.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("Should return false when requests are not empty")
        void should_ReturnFalse_when_RequestsAreNotEmpty() {
            var loggedRequests = new LoggedRequests(List.of(
                    new LoggedRequest("/requests", Map.of())
            ));
            assertThat(loggedRequests.isEmpty()).isFalse();
        }
    }
}
