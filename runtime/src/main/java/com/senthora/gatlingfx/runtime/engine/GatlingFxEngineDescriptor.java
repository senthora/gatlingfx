package com.senthora.gatlingfx.runtime.engine;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

/**
 * Represents the root of JUnit test tree containing
 * all Gatling simulations discovered for execution.
 * <p>
 * Discovered simulations are attached as child
 * {@link SimulationDescriptor} instances and can
 * be accessed through {@link #getChildren()}.
 */
public final class GatlingFxEngineDescriptor extends EngineDescriptor {

    private static final String DISPLAY_NAME = "GatlingFx";

    /**
     * Creates a new engine descriptor.
     *
     * @param id engine unique identifier
     */
    public GatlingFxEngineDescriptor(UniqueId id) {
        super(id, DISPLAY_NAME);
    }
}
