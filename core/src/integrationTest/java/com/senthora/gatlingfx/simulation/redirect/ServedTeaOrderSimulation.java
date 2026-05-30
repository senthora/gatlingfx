package com.senthora.gatlingfx.simulation.redirect;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@GatlingSimulation
@DisplayName("Should follow tea order redirects when redirects are enabled")
public class ServedTeaOrderSimulation extends TeaShopSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return super.protocol().followRedirects(true);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new ServedTeaOrderScenario());
    }
}
