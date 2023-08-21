package TankImplementation.decorator;

import TankSimulation.BlocoCenario;

import java.util.List;

public interface NeighborDecorator {
    List<BlocoCenario> applyDecoration(List<BlocoCenario> neighbors);
}

