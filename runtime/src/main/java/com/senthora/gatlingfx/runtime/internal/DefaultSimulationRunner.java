package com.senthora.gatlingfx.runtime.internal;

import com.senthora.gatlingfx.runtime.api.SimulationRunner;

import io.gatling.app.Runner;
import io.gatling.core.actor.ActorSystem;
import io.gatling.core.cli.GatlingArgs;
import io.gatling.core.config.GatlingConfiguration;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import scala.Option;

import java.nio.file.Path;
import java.util.List;

/**
 * Default {@link SimulationRunner} implementation.
 */
public final class DefaultSimulationRunner implements SimulationRunner {

    private final GatlingConfiguration gatlingConfig = loadConfiguration();

    @Override
    public boolean run(Class<?> simulationClass) {
        return runAll(List.of(simulationClass));
    }

    public boolean runAll() {
        return runAll(SimulationProbe.scan());
    }

    private boolean runAll(List<Class<?>> simulationClasses) {
        var simulationContext = new SimulationExecutionContext();
        SimulationExecution.set(simulationContext);

        try (var actorSystem = new ActorSystem()) {
            var eventLoopGroup = new EpollEventLoopGroup();
            var startupContext = new RunnerStartupContext(actorSystem, eventLoopGroup);
            try {
                for (Class<?> clazz : simulationClasses) {
                    runSimulation(clazz, startupContext);
                }
            }
            finally {
                eventLoopGroup.shutdownGracefully();
            }
        }
        return !simulationContext.failed();
    }

    private void runSimulation(Class<?> simulationClass, RunnerStartupContext context) {
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
        runner.run();
    }

    private static GatlingConfiguration loadConfiguration() {
        System.setProperty(
                "logback.configurationFile",
                "src/resources/logback-test.xml"
        );
        return GatlingConfiguration.load();
    }

    private record RunnerStartupContext(
            ActorSystem actorSystem,
            EventLoopGroup eventLoopGroup
    ) {}
}
