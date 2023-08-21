package TankImplementation.strategy;

import TankSimulation.BlocoCenario;

import java.util.List;

public interface NeighborStrategy {
    List<BlocoCenario> getNeighbors(BlocoCenario[][] matrizBlocos, BlocoCenario node);
}

