package com.senthora.gatlingfx.runtime.support;

import com.senthora.gatlingfx.support.TestSimulation;

import org.junit.jupiter.api.DisplayName;

public class NamedTestSimulations {

    @DisplayName("hello world")
    public static class HelloWorldSimulation extends TestSimulation {}

    @DisplayName("   not trimmed   ")
    public static class DefinitelyNotTrimmedSimulation extends TestSimulation {}
}
