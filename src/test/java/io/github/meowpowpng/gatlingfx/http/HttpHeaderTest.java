package io.github.meowpowpng.gatlingfx.http;

import io.github.meowpowpng.gatlingfx.support.TestHeaders;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {

    @Nested
    @DisplayName("constructor")
    class ConstructorTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when name is null")
        void should_ThrowNullPointerException_when_NameIsNull() {
            assertThatThrownBy(() -> new HttpHeader(null, "value"))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when value is null")
        void should_ThrowNullPointerException_when_ValueIsNull() {
            assertThatThrownBy(() -> new HttpHeader("Content-Type", null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when name is blank")
        void should_ThrowIllegalArgumentException_when_NameIsBlank() {
            assertThatThrownBy(() -> new HttpHeader(" ", "value"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Should create header when name and value are valid")
        void should_CreateHeader_when_NameAndValueAreValid() {
            var header = TestHeaders.jsonContentTypeHeader();

            assertThat(header.name()).isEqualTo("Content-Type");
            assertThat(header.value()).isEqualTo("application/json");
        }
    }

    @Nested
    @DisplayName("of")
    class OfMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when request header is null")
        void should_ThrowNullPointerException_when_RequestHeaderIsNull() {
            assertThatThrownBy(() -> HttpHeader.of(null, "value"))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should create header when request header and value are valid")
        void should_CreateHeader_when_RequestHeaderAndValueAreValid() {
            var header = TestHeaders.jsonContentTypeHeader();

            assertThat(header.name()).isEqualTo("Content-Type");
            assertThat(header.value()).isEqualTo("application/json");
        }
    }
}
