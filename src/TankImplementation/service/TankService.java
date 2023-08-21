package TankImplementation.service;

import TankImplementation.decorator.NeighborDecorator;
import TankImplementation.entity.Tank;
import TankImplementation.strategy.NeighborStrategy;
import TankSimulation.BlocoCenario;
import javaengine.CSprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class TankService {
    private NeighborStrategy neighborStrategy;
    private NeighborDecorator neighborDecorator;

    public void setNeighborStrategy(NeighborStrategy strategy) {
        this.neighborStrategy = strategy;
    }

    public void setNeighborDecorator(NeighborDecorator decorator) {
        this.neighborDecorator = decorator;
    }

    private void initStartNode(BlocoCenario inicio, BlocoCenario fim, PriorityQueue<BlocoCenario> openSet) {
        inicio.custoMovimento = 0;
        inicio.custoEstimadoParaDestino = calcHeuristic(inicio, fim);

        openSet.add(inicio);
    }

    private boolean isGoalReached(BlocoCenario inicio, BlocoCenario fim) {
        return inicio.equals(fim);
    }

    public List<BlocoCenario> getNeighbors(BlocoCenario[][] matrizBlocos, BlocoCenario node) {
        List<BlocoCenario> neighbors = neighborStrategy.getNeighbors(matrizBlocos, node);
        return neighborDecorator.applyDecoration(neighbors);
    }

    public ArrayList<BlocoCenario> findPath(BlocoCenario inicio, BlocoCenario fim, BlocoCenario[][] matrizBlocos) {
        PriorityQueue<BlocoCenario> openSet = new PriorityQueue<>(Comparator.comparingInt(BlocoCenario::retornaCustoTotal));
        Set<BlocoCenario> closedSet = new HashSet<>();
        Map<BlocoCenario, BlocoCenario> cameFrom = new HashMap<>();

        initStartNode(inicio, fim, openSet);

        while (!openSet.isEmpty()) {
            BlocoCenario atual = openSet.poll(); // recupera e remove o primeiro elemento da fila

            if (isGoalReached(atual, fim)) {
                return reconstructPath(cameFrom, atual);
            }

            processNeighbors(atual, fim, openSet, closedSet, cameFrom, matrizBlocos);
        }

        return new ArrayList<>();  // Caminho não encontrado
    }


    private void processNeighbors(BlocoCenario atual,
                                  BlocoCenario fim,
                                  PriorityQueue<BlocoCenario> openSet,
                                  Set<BlocoCenario> closedSet,
                                  Map<BlocoCenario, BlocoCenario> cameFrom,
                                  BlocoCenario[][] matrizBlocos
    ) {
        closedSet.add(atual);

        List<BlocoCenario> neighbors = getNeighbors(matrizBlocos, atual);
        neighbors = neighborDecorator.applyDecoration(neighbors);

        for (BlocoCenario neighbor : neighbors) {
            if (closedSet.contains(neighbor)) {
                continue;
            }

            updateNodeData(atual, neighbor, fim, openSet, cameFrom);
        }
    }


    private void updateNodeData(BlocoCenario atual,
                                BlocoCenario vizinho,
                                BlocoCenario fim,
                                PriorityQueue<BlocoCenario> openSet,
                                Map<BlocoCenario, BlocoCenario> cameFrom) {
        int novoCusto = atual.custoMovimento + vizinho.retornaCustoBloco();

        if (novoCusto < vizinho.custoMovimento) {
            vizinho.custoMovimento = novoCusto;
            vizinho.custoEstimadoParaDestino = calcHeuristic(vizinho, fim);
            cameFrom.put(vizinho, atual);

            if (!openSet.contains(vizinho)) {
                openSet.add(vizinho);
            }
        }
    }


    private int calcHeuristic(BlocoCenario atual, BlocoCenario fim) {
        int dx = Math.abs(atual.linha - fim.linha);
        int dy = Math.abs(atual.coluna - fim.coluna);

        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    private ArrayList<BlocoCenario> reconstructPath(Map<BlocoCenario, BlocoCenario> cameFrom, BlocoCenario atual) {
        ArrayList<BlocoCenario> caminho = new ArrayList<>();
        caminho.add(atual);

        while (cameFrom.containsKey(atual)) {
            BlocoCenario proximo = cameFrom.get(atual);
            caminho.add(proximo);
            atual = proximo;
        }

        Collections.reverse(caminho);
        return caminho;
    }


    public void criaTank(CSprite sprite, BlocoCenario[][] matrizBlocos) {
        new Tank(sprite, matrizBlocos);
    }
}
