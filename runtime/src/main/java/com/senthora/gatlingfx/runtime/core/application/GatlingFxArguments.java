package com.senthora.gatlingfx.runtime.core.application;

import org.jspecify.annotations.Nullable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Optional;

/**
 * Command-line arguments for GatlingFx entrypoint.
 */
@SuppressWarnings("unused")
@Command(name = "gatlingfx", mixinStandardHelpOptions = true)
final class GatlingFxArguments {

    static final String SIMULATION = "--simulation";
    static final String QUIET = "--quiet";
    static final String FAIL_FAST = "--fail-fast";

    @Option(
            names = SIMULATION,
            description = "Fully qualified simulation class name to execute"
    )
    private @Nullable String simulationClassName;

    @Option(
            names = QUIET,
            description = "Suppress runtime console logging"
    )
    private boolean quietLogs;

    @Option(
            names = FAIL_FAST,
            description = "Stop execution after the first failed simulation"
    )
    private boolean failFast;

    Optional<String> simulationClassName() {
        return Optional.ofNullable(simulationClassName);
    }

    boolean quietLogs() {
        return quietLogs;
    }

    boolean failFast() {
        return failFast;
    }
}
