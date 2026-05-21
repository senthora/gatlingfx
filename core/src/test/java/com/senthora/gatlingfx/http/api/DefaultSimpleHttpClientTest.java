package com.senthora.gatlingfx.http.api;

import com.senthora.gatlingfx.http.internal.DefaultSimpleHttpClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimpleHttpClientTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when base URL is null")
        void should_ThrowNullPointerException_when_BaseUrlIsNull() {
            assertThatThrownBy(() -> new DefaultSimpleHttpClient(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("get")
    class GetMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when path is null")
        void should_ThrowNullPointerException_when_PathIsNull() {
            assertThatThrownBy(() -> localhostClient().get(null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("post")
    class PostMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when path is null")
        void should_ThrowNullPointerException_when_PostPathIsNull() {
            assertThatThrownBy(() -> localhostClient().post(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when body is null")
        void should_ThrowNullPointerException_when_PostBodyIsNull() {
            assertThatThrownBy(() -> localhostClient().post("/test", null))
                    .isInstanceOf(NullPointerException.class);
        }
    }

    @Nested
    @DisplayName("baseUrl")
    class BaseUrlMethodTests {

        @Test
        @DisplayName("Should return base URL when client is created")
        void should_ReturnBaseUrl_when_ClientIsCreated() {
            assertThat(localhostClient().baseUrl())
                    .isEqualTo(HttpBaseUrl.LOCALHOST);
        }
    }

    private static DefaultSimpleHttpClient localhostClient() {
        return new DefaultSimpleHttpClient(HttpBaseUrl.LOCALHOST);
    }
}
