package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;

public final class TestSimulations {

    private TestSimulations() {}

    @GatlingSimulation
    public static class SupportedSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class VerySuccessfulSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class SuccessfulSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class FailedSimulation extends TestBaseSimulation {}

    @GatlingSimulation
    public static class UnsupportedSimulation {}

    public static class NonAnnotatedSimulation extends TestBaseSimulation {}

    public static class NonSimulation {}
}
