package com.senthora.gatlingfx.runtime.engine;

import org.junit.jupiter.api.DisplayName;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

/**
 * JUnit test descriptor representing a single
 * Gatling simulation scheduled for execution.
 */
public final class SimulationDescriptor extends AbstractTestDescriptor {

    private final Class<?> simulationClass;

    /**
     * Creates a new simulation descriptor.
     *
     * @param id descriptor unique identifier
     * @param simulationClass simulation class represented by this descriptor
     */
    public SimulationDescriptor(UniqueId id, Class<?> simulationClass) {
        super(id, displayName(simulationClass));
        this.simulationClass = simulationClass;
    }

    /**
     * Returns the simulation class
     * represented by this descriptor.
     */
    public Class<?> simulationClass() {
        return simulationClass;
    }

    private static String displayName(Class<?> simulationClass) {
        var annotation = simulationClass.getAnnotation(DisplayName.class);
        var className = simulationClass.getSimpleName();

        if (annotation != null) {
            return className + " > " + annotation.value();
        }
        return className;
    }

    @Override
    public Type getType() {
        return Type.TEST;
    }
}
