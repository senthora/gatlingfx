package com.senthora.gatlingfx.runtime.core.internal;

import io.gatling.app.RunResult;
import io.gatling.app.Runner;
import io.gatling.core.cli.GatlingArgs;

/**
 * Default {@link GatlingRunner} implementation.
 */
public final class DefaultGatlingRunner implements GatlingRunner {

    @Override
    public RunResult run(GatlingArgs gatlingArgs, Runner runner) {
        return runner.run();
    }
}
