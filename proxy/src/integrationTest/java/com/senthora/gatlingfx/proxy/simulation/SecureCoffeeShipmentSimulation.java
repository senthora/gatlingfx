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
@DisplayName("Should route HTTPS requests to coffee service")
public final class SecureCoffeeShipmentSimulation extends BaseSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return ProxyProtocols.https(TeaShippingNetwork.HTTPS_PROXY, TeaShippingNetwork.COFFEE_WAREHOUSE);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new PrepareCoffeeShipmentScenario());
    }
}
