package com.senthora.gatlingfx.runtime.engine.support;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;

public final class TestSimulations {

    @GatlingSimulation
    public static class VerySuccessfulSimulation {}

    @GatlingSimulation
    public static class SuccessfulSimulation {}

    @GatlingSimulation
    public static class FailedSimulation {}

    public static class NonSimulation {}
}
