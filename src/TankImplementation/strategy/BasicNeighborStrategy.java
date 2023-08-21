package TankImplementation.strategy;

import TankImplementation.strategy.NeighborStrategy;
import TankSimulation.BlocoCenario;

import java.util.ArrayList;
import java.util.List;

public class BasicNeighborStrategy implements NeighborStrategy {
    @Override
    public List<BlocoCenario> getNeighbors(BlocoCenario[][] matrizBlocos, BlocoCenario node) {
        List<BlocoCenario> neighbors = new ArrayList<>();
        int row = node.linha;
        int col = node.coluna;

        if (row > 0) {
            neighbors.add(matrizBlocos[row - 1][col]);
        }

        if (row < matrizBlocos.length - 1) {
            neighbors.add(matrizBlocos[row + 1][col]);
        }

        if (col > 0) {
            neighbors.add(matrizBlocos[row][col - 1]);
        }

        if (col < matrizBlocos[0].length - 1) {
            neighbors.add(matrizBlocos[row][col + 1]);
        }

        return neighbors;
    }
}

