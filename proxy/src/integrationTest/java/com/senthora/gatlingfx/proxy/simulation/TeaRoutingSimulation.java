package com.senthora.gatlingfx.proxy.simulation;

import com.senthora.gatlingfx.proxy.ProxyProtocols;
import com.senthora.gatlingfx.proxy.support.TeaShippingNetwork;
import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.BaseSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@GatlingSimulation
@DisplayName("Should route HTTP requests to tea service")
public final class TeaRoutingSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return ProxyProtocols.http(TeaShippingNetwork.HTTP_PROXY, TeaShippingNetwork.TEA);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new TeaRoutingScenario());
    }
}
