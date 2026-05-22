package com.senthora.gatlingfx.runtime.engine.support;

import org.junit.jupiter.api.DisplayName;

public class NamedTestSimulations {

    @DisplayName("hello world")
    public static class HelloWorldSimulation {}

    @DisplayName("   not trimmed   ")
    public static class DefinitelyNotTrimmedSimulation{}
}
