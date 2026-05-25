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

    @Option(
            names = SIMULATION,
            description = "Fully qualified simulation class name to execute"
    )
    private @Nullable String simulationClassName;

    Optional<String> simulationClassName() {
        return Optional.ofNullable(simulationClassName);
    }
}
