package com.senthora.gatlingfx.proxy;

import com.senthora.gatlingfx.core.BaseSimulation;
import com.senthora.gatlingfx.core.SimulationProtocol;
import com.senthora.gatlingfx.core.SimulationScenario;

import com.senthora.gatlingfx.support.ProxyFixtures;

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
