package com.senthora.gatlingfx.runtime.core.internal.support;

import com.senthora.gatlingfx.runtime.core.api.RuntimeLogLevel;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntime;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeConfig;
import com.senthora.gatlingfx.runtime.core.internal.DefaultGatlingRunner;
import com.senthora.gatlingfx.runtime.core.internal.DefaultSimulationRuntime;
import com.senthora.gatlingfx.runtime.core.internal.GatlingRunner;

public final class SimulationRuntimeBuilder {

    private boolean failFast;
    private RuntimeLogLevel logLevel = RuntimeLogLevel.ERROR;
    private GatlingRunner gatlingRunner = new DefaultGatlingRunner();

    public SimulationRuntimeBuilder withFailFast(boolean value) {
        this.failFast = value;
        return this;
    }

    public SimulationRuntimeBuilder withLogLevel(RuntimeLogLevel value) {
        this.logLevel = value;
        return this;
    }

    public SimulationRuntimeBuilder withGatlingRunner(GatlingRunner value) {
        this.gatlingRunner = value;
        return this;
    }

    public SimulationRuntime build() {
        var config = SimulationRuntimeConfig.create()
                .withFailFast(failFast)
                .withLogLevel(logLevel)
                .build();

        return new DefaultSimulationRuntime(
                gatlingRunner,
                config
        );
    }
}
