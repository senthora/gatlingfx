package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationRunResult;
import com.senthora.gatlingfx.runtime.api.SimulationRunner;

import io.gatling.app.RunResult;
import io.gatling.app.RunResultProcessor;
import io.gatling.app.Runner;
import io.gatling.app.cli.StatusCode;
import io.gatling.core.actor.ActorSystem;
import io.gatling.core.cli.GatlingArgs;
import io.gatling.core.config.GatlingConfiguration;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import scala.Option;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Default {@link SimulationRunner} implementation.
 */
public final class DefaultSimulationRunner implements SimulationRunner {

    private final GatlingConfiguration gatlingConfig = loadConfiguration();

    @Override
    public SimulationRunResult run(Class<?> simulationClass) {
        return new DefaultSimulationRunResult(
                runAll(List.of(simulationClass))
        );
    }

    public SimulationRunResult runAll() {
        return new DefaultSimulationRunResult(
                runAll(SimulationProbe.scan())
        );
    }

    private boolean runAll(List<Class<?>> simulationClasses) {
        try (var actorSystem = new ActorSystem()) {
            var eventLoopGroup = new EpollEventLoopGroup();
            var startupContext = new RunnerStartupContext(actorSystem, eventLoopGroup);
            try {
                for (Class<?> clazz : simulationClasses) {
                    var statusCode = runSimulation(clazz, startupContext);
                    if (statusCode != StatusCode.Success$.MODULE$) {
                        return false;
                    }
                }
            }
            finally {
                shutdownGracefully(eventLoopGroup);
            }
        }
        return true;
    }

    private StatusCode runSimulation(Class<?> simulationClass, RunnerStartupContext context) {
        var gatlingArgs = GatlingArgs.apply(
                Option.apply(simulationClass.getName()),
                Option.empty(),
                false,
                Option.empty(),
                Option.apply(Path.of("build/gatling")),
                Option.empty(),
                Option.empty()
        );
        var runner = Runner.apply(
                context.actorSystem,
                context.eventLoopGroup,
                gatlingArgs,
                gatlingConfig
        );
        RunResult runResult = runner.run();
        return new RunResultProcessor(gatlingArgs, gatlingConfig)
                .processRunResult(runResult);
    }

    private static GatlingConfiguration loadConfiguration() {
        System.setProperty(
                "logback.configurationFile",
                "src/resources/logback-test.xml"
        );
        return GatlingConfiguration.load();
    }

    private static void shutdownGracefully(EventLoopGroup loopGroup) {
        loopGroup.shutdownGracefully(0, 0, TimeUnit.SECONDS)
                .syncUninterruptibly();
    }

    private record RunnerStartupContext(
            ActorSystem actorSystem,
            EventLoopGroup eventLoopGroup
    ) {}
}
