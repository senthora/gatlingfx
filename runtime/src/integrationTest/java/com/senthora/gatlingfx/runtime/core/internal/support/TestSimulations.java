package com.senthora.gatlingfx.runtime.core.internal.support;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.support.TestBaseSimulation;

public final class TestSimulations {

    @GatlingSimulation
    public static class SupportedSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class UnsupportedSimulation {}

    public static class NonAnnotatedSimulation extends TestBaseSimulation {}

    public static class StandardClass {}
}
