package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationRunner;
import com.senthora.gatlingfx.runtime.core.api.SimulationRunnerFactory;
import com.senthora.gatlingfx.runtime.core.api.SimulationRuntimeConfig;

public final class DefaultSimulationRunnerFactory implements SimulationRunnerFactory {

    @Override
    public SimulationRunner create(SimulationRuntimeConfig config) {
        var gatlingRunner = new DefaultGatlingRunner();
        var runtime = new DefaultSimulationRuntime(gatlingRunner, config);

        return new DefaultSimulationRunner(runtime);
    }
}
