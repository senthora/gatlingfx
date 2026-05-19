package io.github.meowpowpng.gatlingfx.proxy;

import io.github.meowpowpng.gatlingfx.core.BaseSimulation;
import io.github.meowpowpng.gatlingfx.core.SimulationProtocol;
import io.github.meowpowpng.gatlingfx.core.SimulationScenario;
import io.github.meowpowpng.gatlingfx.support.ProxyFixtures;

import java.util.List;

public final class CoffeeRoutingSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return ProxyProtocols.http(ProxyFixtures.PROXY, ProxyFixtures.COFFEE);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new CoffeeRoutingScenario());
    }
}
