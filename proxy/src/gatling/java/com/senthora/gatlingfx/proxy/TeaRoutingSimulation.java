package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.simulation.BaseSimulation;
import com.senthora.gatlingfx.simulation.SimulationProtocol;
import com.senthora.gatlingfx.simulation.SimulationScenario;

import com.senthora.gatlingfx.support.ProxyFixtures;

import java.util.List;

public final class TeaRoutingSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return ProxyProtocols.http(ProxyFixtures.HTTP_PROXY, ProxyFixtures.TEA);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new TeaRoutingScenario());
    }
}
