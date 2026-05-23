package com.senthora.gatlingfx.simulation.redirect;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@GatlingSimulation
@DisplayName("Should not follow tea order redirects when redirects are disabled")
public class UnknownTeaOrderSimulation extends TeaShopSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return super.protocol().followRedirects(false);
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new UnknownTeaOrderScenario());
    }
}
