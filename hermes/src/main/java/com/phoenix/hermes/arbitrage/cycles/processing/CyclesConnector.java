package com.phoenix.hermes.arbitrage.cycles.processing;

import com.phoenix.hermes.arbitrage.cycles.impl.MultiConnectedCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.ProfitableCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.ConnectedCycle;
import com.phoenix.hermes.arbitrage.cycles.impl.interfaces.Cycle;
import com.phoenix.hermes.arbitrage.graph.edges.interfaces.DirectedEdge;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CyclesConnector {

    private final Map<Integer, List<Arbitrage>> arbitrageComponentMap = new TreeMap<>();
    private final Map<Integer, Set<Cycle>> foundCyclesComponentMap = new TreeMap<>();

    public void addArbitrageToComponent(int componentId, Arbitrage arbitrage) {
        arbitrageComponentMap.computeIfAbsent(componentId, x -> new ArrayList<>())
                .add(arbitrage);
    }

    public void addCyclesToComponent(int componentId, Set<Cycle> foundCycles) {
        foundCyclesComponentMap.computeIfAbsent(componentId, x -> new HashSet<>())
                .addAll(foundCycles);
    }

    public List<Arbitrage> getArbitragesByComponent(int componentId) {
        return arbitrageComponentMap.get(componentId);
    }

    public Set<Cycle> getAllCycles() {
        return foundCyclesComponentMap.keySet().stream()
                .map(foundCyclesComponentMap::get)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Cycle> getAllCyclesConnected() {
        if (arbitrageComponentMap.isEmpty())
            throw new IllegalArgumentException("No arbitrage has been added to make connections");

        Set<Cycle> cycles = new HashSet<>();

        for (var entry : arbitrageComponentMap.entrySet()) {
            int componentId = entry.getKey();
            List<Arbitrage> arbitrages = entry.getValue();
            Set<Cycle> foundCycles = foundCyclesComponentMap.get(componentId);

            for (Cycle cycle : foundCycles) {
                Cycle connectedCycle = makeConnected(cycle, arbitrages);
                cycles.add(connectedCycle);
            }
        }

        return cycles;
    }

    private Cycle makeConnected(
            Cycle cycle,
            List<Arbitrage> arbitrages
    ) {
        Cycle shiftedCycle = shift(cycle, arbitrages);
        if (shiftedCycle != null)
            return shiftedCycle;
        return connect(cycle, arbitrages);
    }

    private Cycle shift(
            Cycle cycle,
            List<Arbitrage> arbitrages
    ) {
        for (Arbitrage arbitrage : arbitrages) {
            int position = cycle.getPositionOf(arbitrage.getStartVertex());
            if (position != -1) {
                List<DirectedEdge> edges = cycle.getCycleEdges(position);
                return new ProfitableCycle(edges);
            }
        }
        return null;
    }

    private ConnectedCycle connect(
            Cycle cycle,
            List<Arbitrage> arbitrages
    ) {
        ConnectedCycle connectedCycle = new MultiConnectedCycle(cycle.getCycleEdges());

        List<DirectedEdge> edges = connectedCycle.getCycleEdges();
        int startVertex = edges.get(0).getVertexFrom();
        // TODO Сделать коннект по самому короткому пути или по самому прибыльному из всех подключаемых вершин

        for (Arbitrage arbitrage : arbitrages) {
            connectedCycle.connect(
                    arbitrage.getStartVertex(),
                    arbitrage.getPathTo(startVertex)
            );
        }
        return connectedCycle;
    }
}
