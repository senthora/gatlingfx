package com.senthora.gatlingfx.simulation.route;

import com.senthora.gatlingfx.runtime.core.api.GatlingSimulation;
import com.senthora.gatlingfx.simulation.api.SimulationScenario;
import com.senthora.gatlingfx.simulation.support.TeaShopSimulation;

import org.junit.jupiter.api.DisplayName;

import java.util.List;

@GatlingSimulation
@DisplayName("Should serve tea, coffee, and rejected pizza orders")
public class TeaOrderRoutingSimulation extends TeaShopSimulation {

    @Override
    protected List<SimulationScenario> scenarios() {
        return List.of(
                new TeaOrderScenario(),
                new CoffeeOrderScenario(),
                new PizzaOrderScenario()
        );
    }
}
