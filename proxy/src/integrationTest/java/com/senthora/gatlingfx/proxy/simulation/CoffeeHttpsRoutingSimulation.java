package com.senthora.gatlingfx.proxy.simulation;

import com.senthora.gatlingfx.proxy.ProxyProtocols;
import com.senthora.gatlingfx.proxy.support.ProxyFixtures;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import java.util.List;

public final class CoffeeHttpsRoutingSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return ProxyProtocols.https(ProxyFixtures.HTTPS_PROXY, ProxyFixtures.COFFEE);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new CoffeeRoutingScenario());
    }
}
