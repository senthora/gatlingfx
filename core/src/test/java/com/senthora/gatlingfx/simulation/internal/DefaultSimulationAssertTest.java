package com.senthora.gatlingfx.simulation.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultSimulationAssertTest {

    @Nested
    @DisplayName("isEqualTo")
    class IsEqualToMethodTests {

        @Test
        @DisplayName("Should return current assertion chain when values are equal")
        void should_ReturnCurrentAssertionChain_when_ValuesAreEqual() {
            var assertion = new DefaultSimulationAssert<>("value");

            assertThat(assertion.isEqualTo("value"))
                    .isSameAs(assertion);
        }

        @Test
        @DisplayName("Should throw AssertionError when values are not equal")
        void should_ThrowAssertionError_when_ValuesAreNotEqual() {
            var assertion = new DefaultSimulationAssert<>("actual");

            assertThatThrownBy(() -> assertion.isEqualTo("expected"))
                    .isInstanceOf(AssertionError.class);
        }
    }

    @Nested
    @DisplayName("isNotNull")
    class IsNotNullMethodTests {

        @Test
        @DisplayName("Should return current assertion chain when value is non-null")
        void should_ReturnCurrentAssertionChain_when_ValueIsNonNull() {
            var assertion = new DefaultSimulationAssert<>("value");

            assertThat(assertion.isNotNull()).isSameAs(assertion);
        }

        @Test
        @DisplayName("Should throw AssertionError when value is null")
        void should_ThrowAssertionError_when_ValueIsNull() {
            var assertion = new DefaultSimulationAssert<>(null);

            assertThatThrownBy(assertion::isNotNull)
                    .isInstanceOf(AssertionError.class);
        }
    }

    @Nested
    @DisplayName("isNull")
    class IsNullMethodTests {

        @Test
        @DisplayName("Should return current assertion chain when value is null")
        void should_ReturnCurrentAssertionChain_when_ValueIsNull() {
            var assertion = new DefaultSimulationAssert<>(null);

            assertThat(assertion.isNull()).isSameAs(assertion);
        }

        @Test
        @DisplayName("Should throw AssertionError when value is non-null")
        void should_ThrowAssertionError_when_ValueIsNonNull() {
            var assertion = new DefaultSimulationAssert<>("value");

            assertThatThrownBy(assertion::isNull)
                    .isInstanceOf(AssertionError.class);
        }
    }

    @Nested
    @DisplayName("matches")
    class MatchesMethodTests {

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when predicate is null")
        void should_ThrowNullPointerException_when_PredicateIsNull() {
            var assertion = new DefaultSimulationAssert<>("value");

            assertThatThrownBy(() -> assertion.matches(null, "message"))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @SuppressWarnings("DataFlowIssue")
        @DisplayName("Should throw NullPointerException when message is null")
        void should_ThrowNullPointerException_when_MessageIsNull() {
            var assertion = new DefaultSimulationAssert<>("value");

            assertThatThrownBy(() -> assertion.matches(value -> true, null))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("Should return current assertion chain when predicate matches")
        void should_ReturnCurrentAssertionChain_when_PredicateMatches() {
            var assertion = new DefaultSimulationAssert<>("value");
            Predicate<String> predicate = value -> value.startsWith("val");

            var result = assertion.matches(predicate, "message");

            assertThat(result).isSameAs(assertion);
        }

        @Test
        @DisplayName("Should throw AssertionError when predicate does not match")
        void should_ThrowAssertionError_when_PredicateDoesNotMatch() {
            var assertion = new DefaultSimulationAssert<>("value");
            Predicate<String> predicate = value -> value.startsWith("other");

            assertThatThrownBy(() -> assertion.matches(predicate, "message"))
                    .isInstanceOf(AssertionError.class);
        }
    }
}
