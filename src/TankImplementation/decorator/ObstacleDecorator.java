package TankImplementation.decorator;

import TankSimulation.BlocoCenario;

import java.util.List;
import java.util.stream.Collectors;

public class ObstacleDecorator implements NeighborDecorator {
    @Override
    public List<BlocoCenario> applyDecoration(List<BlocoCenario> neighbors) {
        return neighbors.stream()
                .filter(neighbor -> neighbor.retornaCustoBloco() != Integer.MAX_VALUE)
                .collect(Collectors.toList());
    }
}

