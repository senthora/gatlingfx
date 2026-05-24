package com.senthora.gatlingfx.runtime.core.internal;

import io.gatling.app.RunResult;
import io.gatling.app.Runner;
import io.gatling.core.cli.GatlingArgs;

/**
 * Executes Gatling simulations using
 * internal Gatling runner implementation.
 * <p>
 * This abstraction exists primarily to isolate
 * Gatling execution behavior for testing runtime
 * failure and assertion handling.
 */
@FunctionalInterface
public interface GatlingRunner {

    RunResult run(GatlingArgs gatlingArgs, Runner runner);
}
