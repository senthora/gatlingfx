package com.senthora.gatlingfx.simulation.header;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationProtocol;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@GatlingSimulation
@DisplayName("Should reject custom tea orders that do not meet shop standards")
public final class CustomTeaOrderSimulation extends TeaShopSimulation {

    @Override
    protected SimulationProtocol protocol() {
        return super.protocol().header("X-Tea-Strength", "vanilla");
    }

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(new CustomTeaOrderScenario());
    }
}
