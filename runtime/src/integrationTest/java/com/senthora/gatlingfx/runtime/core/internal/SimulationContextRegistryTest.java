package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationContext;
import com.senthora.gatlingfx.simulation.api.event.ContextCreatedEvent;
import com.senthora.gatlingfx.simulation.internal.AbstractSimulationEventTest;
import com.senthora.gatlingfx.support.AbstractTestSimulation;
import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulationContextRegistryTest extends AbstractSimulationEventTest {

    @Override
    @AfterEach
    protected void teardownSimulationEventTest() {
        super.teardownSimulationEventTest();

        SimulationContextRegistry.reset();
    }

    @Test
    @DisplayName("Should register published context creation events after registry initialization")
    void should_RegisterPublishedContextCreationEvents_after_RegistryInitialization() {
        var expected = createContext(TestSimulation.class);

        SimulationContextRegistry.initialize();

        publishEvent(new ContextCreatedEvent(expected));

        var actual = getContext(TestSimulation.class);
        assertThat(actual).contains(expected);
    }

    @Test
    @DisplayName("Should not process events multiple times when initialized repeatedly")
    void should_NotProcessEventsMultipleTimes_when_InitializedRepeatedly() {
        var expected = createContext(TestSimulation.class);

        SimulationContextRegistry.initialize();
        SimulationContextRegistry.initialize();

        publishEvent(new ContextCreatedEvent(expected));

        var actual = getContext(TestSimulation.class);
        assertThat(actual).contains(expected);
    }

    @Test
    @DisplayName("Should return registered context when matching context exists")
    void should_ReturnRegisteredContext_when_MatchingContextExists() {
        var expected = createContext(TestSimulation.class);

        SimulationContextRegistry.initialize();
        publishEvent(new ContextCreatedEvent(expected));

        assertThat(getContext(TestSimulation.class)).contains(expected);
    }

    @Test
    @DisplayName("Should register contexts independently when simulation classes differ")
    void should_RegisterContextsIndependently_when_SimulationClassesDiffer() {
        var first = createContext(TestSimulation.class);
        var second = createContext(AnotherTestSimulation.class);

        SimulationContextRegistry.initialize();

        publishEvent(new ContextCreatedEvent(first));
        publishEvent(new ContextCreatedEvent(second));

        assertThat(getContext(TestSimulation.class)).contains(first);
        assertThat(getContext(AnotherTestSimulation.class)).contains(second);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when context is already registered")
    void should_ThrowIllegalStateException_when_ContextIsAlreadyRegistered() {
        var context = createContext(TestSimulation.class);

        SimulationContextRegistry.initialize();

        publishEvent(new ContextCreatedEvent(context));

        assertThatThrownBy(() -> publishEvent(new ContextCreatedEvent(context)))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Should remove all registered contexts when registry is cleared")
    void should_RemoveAllRegisteredContexts_when_RegistryIsCleared() {
        var context = createContext(TestSimulation.class);

        SimulationContextRegistry.initialize();
        publishEvent(new ContextCreatedEvent(context));
        SimulationContextRegistry.reset();

        assertThat(getContext(TestSimulation.class)).isEmpty();
    }

    private static SimulationContext createContext(Class<? extends BaseSimulation> simulation) {
        return SimulationContext.create(simulation);
    }

    private static Optional<SimulationContext> getContext(Class<? extends BaseSimulation> simulation) {
        return SimulationContextRegistry.get(simulation);
    }

    private static final class AnotherTestSimulation extends AbstractTestSimulation {}
}
