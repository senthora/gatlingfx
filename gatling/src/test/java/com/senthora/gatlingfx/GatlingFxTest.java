package com.senthora.gatlingfx;

import com.senthora.gatlingfx.runtime.internal.DefaultSimulationRunner;

import org.junit.jupiter.api.Test;

class GatlingFxTest {

    @Test
    void should_Succeed_when_RunningAllGatlingSimulations() {
        new DefaultSimulationRunner().runAll().assertSuccess();
    }
}
