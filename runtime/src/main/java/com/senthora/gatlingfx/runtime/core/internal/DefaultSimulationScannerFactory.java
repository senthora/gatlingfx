package com.senthora.gatlingfx.runtime.core.internal;

import com.senthora.gatlingfx.runtime.core.api.SimulationScanner;
import com.senthora.gatlingfx.runtime.core.api.SimulationScannerFactory;

public final class DefaultSimulationScannerFactory implements SimulationScannerFactory {

    @Override
    public SimulationScanner create() {
        return new ClasspathSimulationScanner();
    }
}
