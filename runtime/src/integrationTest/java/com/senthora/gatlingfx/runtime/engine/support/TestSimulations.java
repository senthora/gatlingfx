package com.senthora.gatlingfx.runtime.engine.support;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.runtime.support.TestBaseSimulation;

public final class TestSimulations {

    @GatlingSimulation
    public static class VerySuccessfulSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class SuccessfulSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class FailedSimulation extends TestBaseSimulation {}

    public static class NonSimulation extends TestBaseSimulation {}
}
