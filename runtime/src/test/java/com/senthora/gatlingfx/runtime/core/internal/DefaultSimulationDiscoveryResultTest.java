package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.support.TestSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

class DefaultSimulationDiscoveryResultTest {

    @Test
    @DisplayName("Should return supported simulations when result is created")
    void should_ReturnSupportedSimulations_when_ResultIsCreated() {
        var expected = TestSimulation.class;
        var result = new DefaultSimulationDiscoveryResult(
                List.of(expected),
                List.of()
        );
        assertThat(result.supported()).containsExactly(expected);
    }

    @Test
    @DisplayName("Should return unsupported simulations when result is created")
    void should_ReturnUnsupportedSimulations_when_ResultIsCreated() {
        var expected = String.class;
        var result = new DefaultSimulationDiscoveryResult(
                List.of(),
                List.of(expected)
        );
        assertThat(result.unsupported()).containsExactly(expected);
    }

    @Test
    @DisplayName("Should create immutable supported simulations list when result is created")
    void should_CreateImmutableSupportedSimulationsList_when_ResultIsCreated() {
        var result = new DefaultSimulationDiscoveryResult(
                List.of(TestSimulation.class),
                List.of()
        );
        assertThatThrownBy(() -> result.supported().add(TestSimulation.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Should create immutable unsupported simulations list when result is created")
    void should_CreateImmutableUnsupportedSimulationsList_when_ResultIsCreated() {
        var result = new DefaultSimulationDiscoveryResult(
                List.of(),
                List.of(String.class)
        );
        assertThatThrownBy(() -> result.unsupported().add(Object.class))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Should create defensive copy of supported simulations when result is created")
    void should_CreateDefensiveCopyOfSupportedSimulations_when_ResultIsCreated() {
        var expected = TestSimulation.class;
        var supported = new ArrayList<Class<? extends BaseSimulation>>();
        supported.add(expected);

        var result = new DefaultSimulationDiscoveryResult(
                supported,
                List.of()
        );
        supported.clear();

        assertThat(result.supported()).containsExactly(expected);
    }

    @Test
    @DisplayName("Should create defensive copy of unsupported simulations when result is created")
    void should_CreateDefensiveCopyOfUnsupportedSimulations_when_ResultIsCreated() {
        var expected = String.class;
        var unsupported = new ArrayList<Class<?>>();
        unsupported.add(expected);

        var result = new DefaultSimulationDiscoveryResult(
                List.of(),
                unsupported
        );
        unsupported.clear();

        assertThat(result.unsupported()).containsExactly(expected);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when supported simulations are null")
    void should_ThrowNullPointerException_when_SupportedSimulationsAreNull() {
        var thrown = catchThrowable(() -> new DefaultSimulationDiscoveryResult(
                null,
                List.of()
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    @DisplayName("Should throw NullPointerException when unsupported simulations are null")
    void should_ThrowNullPointerException_when_UnsupportedSimulationsAreNull() {
        var thrown = catchThrowable(() -> new DefaultSimulationDiscoveryResult(
                List.of(),
                null
        ));
        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
